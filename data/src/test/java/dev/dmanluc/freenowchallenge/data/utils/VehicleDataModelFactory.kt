package dev.dmanluc.freenowchallenge.data.utils

import dev.dmanluc.freenowchallenge.data.datasource.model.VehicleDataModel
import dev.dmanluc.freenowchallenge.domain.model.FleetType
import kotlin.random.Random

object VehicleDataModelFactory : ModelFactory<VehicleDataModel> {

    override fun createOne(): VehicleDataModel {
        return VehicleDataModel(
            Random.nextInt(),
            Pair(Random.nextDouble(-90.0, 90.0), Random.nextDouble(-180.0, 180.0)),
            FleetType.values().map { it.description }.random().uppercase(),
            Random.nextDouble(360.0)
        )
    }

}