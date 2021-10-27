package dev.dmanluc.freenowchallenge.robot

import dev.dmanluc.freenowchallenge.fixture.vehiclePoisError
import dev.dmanluc.freenowchallenge.fixture.vehiclePoisSuccess
import dev.dmanluc.freenowchallenge.fixture.vehiclePoisSuccessEmpty
import pl.droidsonroids.testing.mockwebserver.FixtureDispatcher
import javax.inject.Inject

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Definition of a robot (based on Robot Pattern) which handles different fixture dispatchers
 *
 */
class NetworkRobot @Inject constructor(
    private val dispatcher: FixtureDispatcher
) {
    fun givenVehiclePoisSuccess() = dispatcher.vehiclePoisSuccess()

    fun givenEmptyVehiclePoisSuccess() = dispatcher.vehiclePoisSuccessEmpty()

    fun givenVehiclePoisError() = dispatcher.vehiclePoisError()
}

