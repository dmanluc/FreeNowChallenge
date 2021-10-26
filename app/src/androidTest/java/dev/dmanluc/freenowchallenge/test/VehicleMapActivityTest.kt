package dev.dmanluc.freenowchallenge.test

import androidx.test.espresso.intent.Intents
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.dmanluc.freenowchallenge.appContext
import dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap.VehiclesMapActivity
import dev.dmanluc.freenowchallenge.robot.NetworkRobot
import dev.dmanluc.freenowchallenge.robot.vehiclePoiList
import dev.dmanluc.freenowchallenge.rule.ConditionWatcherTimeoutRule
import dev.dmanluc.freenowchallenge.rule.MockWebServerRule
import dev.dmanluc.freenowchallenge.rule.lazyActivityScenarioRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class VehicleMapActivityTest {

    companion object {
        private const val WATCHER_TIMEOUT: Int = 5_000
    }

    @get:Rule(order = 0)
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule(appContext)

    @get:Rule(order = 2)
    val activity = lazyActivityScenarioRule<VehiclesMapActivity>()
    
    @get:Rule(order = 3)
    val conditionWatcherTimeoutRule = ConditionWatcherTimeoutRule(timeout = WATCHER_TIMEOUT)

    @Inject
    lateinit var network: NetworkRobot

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenSuccessFetchingNotEmpty_shouldShowVehiclePoisList() {
        activity.launch()

        vehiclePoiList {
            network.givenVehiclePoisSuccess()
            checkVehiclePois(30)
        }
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenSuccessFetchingEmpty_shouldShowEmptyPoisList() {
        activity.launch()

        vehiclePoiList {
            network.givenEmptyVehiclePoisSuccess()
            isEmptyListMessageShown()
        }
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenErrorFetching_shouldShowErrorMessage() {
        activity.launch()

        vehiclePoiList {
            network.givenVehiclePoisError()
            isErrorMessageShown()
        }
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenErrorFetchingAndRetryWithSuccessNotEmpty_shouldShowVehiclePoisList() {
        activity.launch()

        vehiclePoiList {
            network.givenVehiclePoisError()
            isErrorMessageShown()
            network.givenVehiclePoisSuccess()
            retryFetchingVehiclePois()
            checkVehiclePois(30)
        }
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenErrorFetchingAndRetryWithSuccessEmpty_shouldShowEmptyVehiclePoisList() {
        activity.launch()

        vehiclePoiList {
            network.givenVehiclePoisError()
            isErrorMessageShown()
            network.givenEmptyVehiclePoisSuccess()
            retryFetchingVehiclePois()
            isEmptyListMessageShown()
        }
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenErrorFetchingAndRetryWithError_shouldShowErrorMessage() {
        activity.launch()

        vehiclePoiList {
            network.givenVehiclePoisError()
            isErrorMessageShown()
            retryFetchingVehiclePois()
            isErrorMessageShown()
        }
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenEmptyFetchingAndRetryWithSuccessNotEmpty_shouldShowVehiclePoisList() {
        activity.launch()

        vehiclePoiList {
            network.givenEmptyVehiclePoisSuccess()
            isEmptyListMessageShown()
            network.givenVehiclePoisSuccess()
            retryFetchingVehiclePois()
            checkVehiclePois(30)
        }
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenEmptyFetchingAndRetryWithSuccessEmpty_shouldShowEmptyVehiclePoisList() {
        activity.launch()

        vehiclePoiList {
            network.givenEmptyVehiclePoisSuccess()
            isEmptyListMessageShown()
            retryFetchingVehiclePois()
            isEmptyListMessageShown()
        }
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenEmptyFetchingAndRetryWithError_shouldShowErrorMessage() {
        activity.launch()

        vehiclePoiList {
            network.givenEmptyVehiclePoisSuccess()
            isEmptyListMessageShown()
            network.givenVehiclePoisError()
            retryFetchingVehiclePois()
            isErrorMessageShown()
        }
    }

}
