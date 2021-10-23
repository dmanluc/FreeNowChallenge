package dev.dmanluc.freenowchallenge.domain.model

data class Vehicle(
    val id: Int,
    val mapCoordinate: MapCoordinate,
    val fleetType: FleetType,
    val mapBearing: Double
)