package dev.dmanluc.freenowchallenge.fixture

import pl.droidsonroids.testing.mockwebserver.FixtureDispatcher
import pl.droidsonroids.testing.mockwebserver.condition.HTTPMethod
import pl.droidsonroids.testing.mockwebserver.condition.PathQueryConditionFactory

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Definition of fixture dispatchers to work with in UI tests
 *
 */
fun FixtureDispatcher.defaults() {
    vehiclePoisSuccess()
}

fun FixtureDispatcher.vehiclePoisSuccess() = map("" to "vehicle_pois_success")

fun FixtureDispatcher.vehiclePoisSuccessEmpty() = map("" to "vehicle_pois_success_empty")

fun FixtureDispatcher.vehiclePoisError() = map("" to "server_error")

fun FixtureDispatcher.map(pair: Pair<String, String>, httpMethod: HTTPMethod = HTTPMethod.GET) {
    with(PathQueryConditionFactory("/")) {
        putResponse(withPathSuffix(pair.first, httpMethod), pair.second)
    }

}
