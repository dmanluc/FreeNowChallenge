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

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * UI test for vehicle map activity. Only interaction with vehicle POI list is done due to
 * lack of support for testing map interaction with Espresso.
 *
 */
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
    val activityScenarioRule = lazyActivityScenarioRule<VehiclesMapActivity>()

    @get:Rule(order = 3)
    val conditionWatcherTimeoutRule = ConditionWatcherTimeoutRule(timeout = WATCHER_TIMEOUT)

    @Inject
    lateinit var network: NetworkRobot

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        Intents.init()

        activityScenarioRule.launch()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenSuccessFetchingNotEmpty_shouldShowVehiclePoisList() {
        vehiclePoiList {
            checkVehiclePois(30)
        }
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenSuccessFetchingEmpty_shouldShowEmptyPoisList() {
        vehiclePoiList {
            network.givenEmptyVehiclePoisSuccess()
            isEmptyListMessageShown()
        }
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenErrorFetching_shouldShowErrorMessage() {
        vehiclePoiList {
            network.givenVehiclePoisError()
            isErrorMessageShown()
        }
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenErrorFetchingAndRetryWithSuccessNotEmpty_shouldShowVehiclePoisList() {
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
        vehiclePoiList {
            network.givenVehiclePoisError()
            isErrorMessageShown()
            retryFetchingVehiclePois()
            isErrorMessageShown()
        }
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenEmptyFetchingAndRetryWithSuccessNotEmpty_shouldShowVehiclePoisList() {
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
        vehiclePoiList {
            network.givenEmptyVehiclePoisSuccess()
            isEmptyListMessageShown()
            retryFetchingVehiclePois()
            isEmptyListMessageShown()
        }
    }

    @Test
    fun loadVehiclePoisOnActivityLaunch_whenEmptyFetchingAndRetryWithError_shouldShowErrorMessage() {
        vehiclePoiList {
            network.givenEmptyVehiclePoisSuccess()
            isEmptyListMessageShown()
            network.givenVehiclePoisError()
            retryFetchingVehiclePois()
            isErrorMessageShown()
        }
    }

}
