package dev.dmanluc.freenowchallenge.data.utils

import dev.dmanluc.freenowchallenge.data.datasource.model.VehiclePoiApiModel
import dev.dmanluc.freenowchallenge.data.datasource.model.VehiclePoiCoordinateApiModel
import dev.dmanluc.freenowchallenge.domain.model.FleetType
import kotlin.random.Random

object VehiclePoiApiModelFactory : ModelFactory<VehiclePoiApiModel> {

    override fun createOne(): VehiclePoiApiModel {
        return VehiclePoiApiModel(
            Random.nextInt(),
            VehiclePoiCoordinateApiModel(
                Random.nextDouble(-90.0, 90.0),
                Random.nextDouble(-180.0, 180.0)
            ),
            FleetType.values().random().name,
            Random.nextDouble(360.0)
        )
    }

}