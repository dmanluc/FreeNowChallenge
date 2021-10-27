package dev.dmanluc.freenowchallenge.domain.model

data class Vehicle(
    val id: String,
    val mapCoordinate: MapCoordinate,
    val fleetType: FleetType,
    val mapBearing: Double
)