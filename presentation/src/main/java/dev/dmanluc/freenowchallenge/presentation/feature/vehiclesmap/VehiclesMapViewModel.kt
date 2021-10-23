package dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.dmanluc.freenowchallenge.domain.model.MapBounds
import dev.dmanluc.freenowchallenge.domain.model.MapCoordinate
import dev.dmanluc.freenowchallenge.domain.model.Vehicle
import dev.dmanluc.freenowchallenge.domain.usecase.GetVehiclesUseCase
import dev.dmanluc.freenowchallenge.presentation.di.DispatcherProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehiclesMapViewModel @Inject constructor(
    private val getVehiclesUseCase: GetVehiclesUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val mutableVehiclesLiveData = MutableLiveData<List<Vehicle>>()
    val vehiclesLiveData: LiveData<List<Vehicle>> get() = mutableVehiclesLiveData

    companion object {
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

            getVehiclesUseCase(
                GetVehiclesUseCase.Params(
                    MapBounds(
                        Pair(
                            firstBoundCoordinate,
                            secondBoundCoordinate
                        )
                    )
                )
            )
                .fold(
                    ifRight = { vehicleList ->
                        mutableVehiclesLiveData.value = vehicleList
                    },
                    ifLeft = { throwableError ->
                        println(throwableError)
                    }
                )
        }
    }

}