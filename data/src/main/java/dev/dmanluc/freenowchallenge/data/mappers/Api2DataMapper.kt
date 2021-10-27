package dev.dmanluc.freenowchallenge.data.mappers

import dev.dmanluc.freenowchallenge.data.datasource.model.VehicleDataModel
import dev.dmanluc.freenowchallenge.data.datasource.model.VehiclePoiApiModel
import dev.dmanluc.freenowchallenge.data.datasource.model.VehiclePoisApiModel

fun VehiclePoisApiModel.toData(): List<VehicleDataModel> =
    vehiclePoiList.filter { it.id != null }.map { it.toData() }

private fun VehiclePoiApiModel.toData() = VehicleDataModel(
    id = id?.toString().orEmpty(),
    mapCoordinate = Pair(coordinate?.latitude ?: 0.0, coordinate?.longitude ?: 0.0),
    fleetType = fleetType?.uppercase().orEmpty(),
    mapBearing = heading ?: 0.0
)
