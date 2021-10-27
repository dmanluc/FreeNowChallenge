package dev.dmanluc.freenowchallenge.robot

import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import dev.dmanluc.freenowchallenge.R
import dev.dmanluc.freenowchallenge.sync.ViewConditionWatcher.waitUntilListIsLoaded
import dev.dmanluc.freenowchallenge.sync.ViewConditionWatcher.waitUntilViewIsDisplayed

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Definition of a robot (based on Robot Pattern) to interact with vehicle POI list
 *
 */
fun vehiclePoiList(func: VehiclePoiRobot.() -> Unit): VehiclePoiRobot {
    waitUntilViewIsDisplayed(R.id.bottomSheetView)
    return VehiclePoiRobot().apply(func)
}

class VehiclePoiRobot {

    fun checkVehiclePois(vehicleItemsCount: Int) {
        waitUntilListIsLoaded(R.id.vehiclesList)
        assertNotDisplayed(R.id.errorView)
        assertNotDisplayed(R.id.retryButton)
        assertListItemCount(R.id.vehiclesList, vehicleItemsCount)
    }

    fun isEmptyListMessageShown() {
        waitUntilViewIsDisplayed(R.id.errorView)
        assertNotDisplayed(R.id.vehiclesList)
        assertDisplayed(R.id.retryButton)
        assertDisplayed(R.string.empty_vehicle_pois_view_text)
    }

    fun isErrorMessageShown() {
        waitUntilViewIsDisplayed(R.id.errorView)
        assertNotDisplayed(R.id.vehiclesList)
        assertDisplayed(R.id.retryButton)
    }

    fun retryFetchingVehiclePois() {
        clickOn(R.id.retryButton)
    }

}
