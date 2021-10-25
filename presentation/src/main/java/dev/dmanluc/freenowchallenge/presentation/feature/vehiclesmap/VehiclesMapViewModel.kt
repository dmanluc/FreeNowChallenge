package dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dmanluc.freenowchallenge.domain.model.MapBounds
import dev.dmanluc.freenowchallenge.domain.model.MapCoordinate
import dev.dmanluc.freenowchallenge.domain.model.Vehicle
import dev.dmanluc.freenowchallenge.domain.usecase.GetVehiclesUseCase
import dev.dmanluc.freenowchallenge.presentation.di.DispatcherProvider
import dev.dmanluc.freenowchallenge.presentation.extensions.toUiModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehiclesMapViewModel @Inject constructor(
    private val getVehiclesUseCase: GetVehiclesUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val stateHandle: SavedStateHandle,
) : ViewModel() {

    private val mutableVehiclesStateLiveData = MutableLiveData<VehicleMapViewState>(stateHandle[STATE_KEY])
    val vehiclesStateLiveData: LiveData<VehicleMapViewState> get() = mutableVehiclesStateLiveData

    companion object {
        @VisibleForTesting const val STATE_KEY = "savedVehicleMapState"
        val mapBounds = LatLngBounds(
            LatLng(53.394655, 9.757589),
            LatLng(53.694865, 10.099891)
        )
    }

    fun getVehiclesFromMapBounds(bounds: LatLngBounds) {
        viewModelScope.launch(dispatcherProvider.main()) {
            val firstBoundCoordinate =
                MapCoordinate(Pair(bounds.northeast.latitude, bounds.southwest.longitude))
            val secondBoundCoordinate =
                MapCoordinate(Pair(bounds.southwest.latitude, bounds.northeast.longitude))

            mutableVehiclesStateLiveData.value = VehicleMapViewState.FirstLoading

            getVehiclesUseCase(
                GetVehiclesUseCase.Params(
                    MapBounds(
                        Pair(
                            firstBoundCoordinate,
                            secondBoundCoordinate
                        )
                    )
                )
            ).fold(
                ifRight = ::handleSuccessVehiclesLoadedResult,
                ifLeft = ::handleErrorVehiclesLoadedResult
            )
        }
    }

    private fun handleSuccessVehiclesLoadedResult(vehicleList: List<Vehicle>) {
        if (vehicleList.isEmpty()) {
            applyAndSaveState(VehicleMapViewState.EmptyVehiclesLoaded)
            return
        }

        val vehicleViewItems = vehicleList.map { it.toUiModel() }
        applyAndSaveState(VehicleMapViewState.VehiclesLoaded(vehicleViewItems))
    }

    private fun handleErrorVehiclesLoadedResult(error: Throwable) {
        applyAndSaveState(VehicleMapViewState.VehiclesLoadedError(error))
    }

    private fun applyAndSaveState(state: VehicleMapViewState) {
        stateHandle[STATE_KEY] = state
        mutableVehiclesStateLiveData.value = state
    }

}