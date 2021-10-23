package dev.dmanluc.freenowchallenge.data.datasource.mapper

import dev.dmanluc.freenowchallenge.data.datasource.model.VehiclePoi
import dev.dmanluc.freenowchallenge.domain.model.FleetType
import dev.dmanluc.freenowchallenge.domain.model.MapCoordinate
import dev.dmanluc.freenowchallenge.domain.model.Vehicle

fun VehiclePoi.toDomainModel(): Vehicle = Vehicle(
    id = id ?: 0,
    mapCoordinate = MapCoordinate(Pair(coordinate?.latitude ?: 0.0, coordinate?.longitude ?: 0.0)),
    fleetType = FleetType.valueOf(fleetType?.uppercase().orEmpty()),
    mapBearing = heading ?: 0.0
)
