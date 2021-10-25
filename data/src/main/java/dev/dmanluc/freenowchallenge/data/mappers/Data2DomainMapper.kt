package dev.dmanluc.freenowchallenge.data.mappers

import dev.dmanluc.freenowchallenge.data.datasource.model.VehicleDataModel
import dev.dmanluc.freenowchallenge.domain.model.FleetType
import dev.dmanluc.freenowchallenge.domain.model.MapCoordinate
import dev.dmanluc.freenowchallenge.domain.model.Vehicle

fun VehicleDataModel.toDomainModel(): Vehicle = Vehicle(
    id = id,
    mapCoordinate = MapCoordinate(Pair(mapCoordinate.first, mapCoordinate.second)),
    fleetType = FleetType.valueOf(fleetType),
    mapBearing = mapBearing
)
