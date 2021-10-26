package dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import arrow.core.left
import arrow.core.right
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dev.dmanluc.freenowchallenge.domain.model.DomainError
import dev.dmanluc.freenowchallenge.domain.model.Vehicle
import dev.dmanluc.freenowchallenge.domain.usecase.GetVehiclesUseCase
import dev.dmanluc.freenowchallenge.presentation.R
import dev.dmanluc.freenowchallenge.presentation.di.DispatcherProvider
import dev.dmanluc.freenowchallenge.presentation.extensions.toUiModel
import dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap.VehiclesMapViewModel.Companion.STATE_KEY
import dev.dmanluc.freenowchallenge.presentation.utils.MainCoroutineRule
import dev.dmanluc.freenowchallenge.presentation.utils.VehicleModelFactory
import dev.dmanluc.freenowchallenge.presentation.utils.observeForTesting
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class VehiclesMapViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var SUT: VehiclesMapViewModel

    private val testDispatcherProvider = object : DispatcherProvider {
        override fun default(): CoroutineDispatcher = testCoroutineDispatcher
        override fun io(): CoroutineDispatcher = testCoroutineDispatcher
        override fun main(): CoroutineDispatcher = testCoroutineDispatcher
        override fun unconfined(): CoroutineDispatcher = testCoroutineDispatcher
    }

    private val getVehiclesUseCase: GetVehiclesUseCase = mockk()

    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    @Before
    fun setUp() {
        SUT = VehiclesMapViewModel(getVehiclesUseCase, testDispatcherProvider, savedStateHandle)
    }

    @Test
    fun `verify get vehicles on happy path interaction and return not empty vehicle list`() =
        runBlockingTest {
            val mockVehicles = VehicleModelFactory.createMany(20)
            val viewStateObserver = mockk<Observer<VehicleMapViewState>>(relaxed = true)

            coEvery {
                getVehiclesUseCase.invoke(any())
            } returns mockVehicles.right()

            SUT.vehiclesStateLiveData.observeForTesting(viewStateObserver) {
                SUT.getVehiclesFromMapBounds(LatLngBounds(LatLng(50.0, 56.0), LatLng(58.0, 45.0)))

                verify(exactly = 1) {
                    viewStateObserver.onChanged(VehicleMapViewState.FirstLoading)
                    val vehiclesLoadedViewState =
                        VehicleMapViewState.VehiclesLoaded(mockVehicles.map { it.toUiModel() })
                    viewStateObserver.onChanged(vehiclesLoadedViewState)
                    savedStateHandle.set(STATE_KEY, vehiclesLoadedViewState)
                }
            }
        }

    @Test
    fun `verify get vehicles on happy path interaction and return empty vehicle list`() =
        runBlockingTest {
            val mockEmptyVehicles = emptyList<Vehicle>()
            val viewStateObserver = mockk<Observer<VehicleMapViewState>>(relaxed = true)

            coEvery {
                getVehiclesUseCase.invoke(any())
            } returns mockEmptyVehicles.right()

            SUT.vehiclesStateLiveData.observeForTesting(viewStateObserver) {
                SUT.getVehiclesFromMapBounds(LatLngBounds(LatLng(50.0, 56.0), LatLng(58.0, 45.0)))

                verify(exactly = 1) {
                    viewStateObserver.onChanged(VehicleMapViewState.FirstLoading)
                    val emptyVehiclesLoadedViewState =
                        VehicleMapViewState.EmptyVehiclesLoaded(R.string.empty_vehicle_pois_view_text)
                    viewStateObserver.onChanged(emptyVehiclesLoadedViewState)
                    savedStateHandle.set(STATE_KEY, emptyVehiclesLoadedViewState)
                }
            }
        }

    @Test
    fun `verify get vehicles on error interaction and return domain http error exception`() =
        runBlockingTest {
            val domainError = DomainError.HttpError(400, "Error retrieving vehicles")
            val viewStateObserver = mockk<Observer<VehicleMapViewState>>(relaxed = true)

            coEvery {
                getVehiclesUseCase.invoke(any())
            } returns domainError.left()

            SUT.vehiclesStateLiveData.observeForTesting(viewStateObserver) {
                SUT.getVehiclesFromMapBounds(LatLngBounds(LatLng(50.0, 56.0), LatLng(58.0, 45.0)))

                verify(exactly = 1) {
                    viewStateObserver.onChanged(VehicleMapViewState.FirstLoading)
                    val vehiclesLoadedErrorViewState =
                        VehicleMapViewState.VehiclesLoadedError(domainError.body)
                    viewStateObserver.onChanged(vehiclesLoadedErrorViewState)
                    savedStateHandle.set(STATE_KEY, vehiclesLoadedErrorViewState)
                }
            }
        }

    @Test
    fun `verify get vehicles on error interaction and return domain connectivity error`() =
        runBlockingTest {
            val domainError = DomainError.NetworkError(Throwable("Error retrieving vehicles"))
            val viewStateObserver = mockk<Observer<VehicleMapViewState>>(relaxed = true)

            coEvery {
                getVehiclesUseCase.invoke(any())
            } returns domainError.left()

            SUT.vehiclesStateLiveData.observeForTesting(viewStateObserver) {
                SUT.getVehiclesFromMapBounds(LatLngBounds(LatLng(50.0, 56.0), LatLng(58.0, 45.0)))

                verify(exactly = 1) {
                    viewStateObserver.onChanged(VehicleMapViewState.FirstLoading)
                    val vehiclesLoadedErrorViewState =
                        VehicleMapViewState.VehiclesLoadedConnectivityError(R.string.no_internet_connection_text)
                    viewStateObserver.onChanged(vehiclesLoadedErrorViewState)
                    savedStateHandle.set(STATE_KEY, vehiclesLoadedErrorViewState)
                }
            }
        }

    @Test
    fun `verify get vehicles on error interaction and return error exception`() = runBlockingTest {
        val domainError = DomainError.UnknownError(Throwable("Error retrieving vehicles"))
        val viewStateObserver = mockk<Observer<VehicleMapViewState>>(relaxed = true)

        coEvery {
            getVehiclesUseCase.invoke(any())
        } returns domainError.left()

        SUT.vehiclesStateLiveData.observeForTesting(viewStateObserver) {
            SUT.getVehiclesFromMapBounds(LatLngBounds(LatLng(50.0, 56.0), LatLng(58.0, 45.0)))

            verify(exactly = 1) {
                viewStateObserver.onChanged(VehicleMapViewState.FirstLoading)
                val vehiclesLoadedErrorViewState =
                    VehicleMapViewState.VehiclesLoadedError(domainError.throwable.message.orEmpty())
                viewStateObserver.onChanged(vehiclesLoadedErrorViewState)
                savedStateHandle.set(STATE_KEY, vehiclesLoadedErrorViewState)
            }
        }
    }

}