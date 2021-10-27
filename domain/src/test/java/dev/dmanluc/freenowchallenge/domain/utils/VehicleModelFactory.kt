package dev.dmanluc.freenowchallenge.domain.utils

import dev.dmanluc.freenowchallenge.domain.model.FleetType
import dev.dmanluc.freenowchallenge.domain.model.MapCoordinate
import dev.dmanluc.freenowchallenge.domain.model.Vehicle
import kotlin.random.Random

object VehicleModelFactory : ModelFactory<Vehicle> {

    override fun createOne(): Vehicle {
        return Vehicle(
            Random.nextInt().toString(),
            MapCoordinate(Pair(Random.nextDouble(-90.0, 90.0), Random.nextDouble(-180.0, 180.0))),
            FleetType.values().random(),
            Random.nextDouble(360.0)
        )
    }

}