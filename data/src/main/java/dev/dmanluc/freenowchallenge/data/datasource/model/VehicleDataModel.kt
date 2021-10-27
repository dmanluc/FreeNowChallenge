package dev.dmanluc.freenowchallenge.data.datasource.model

data class VehicleDataModel(
    val id: String,
    val mapCoordinate: Pair<Double, Double>,
    val fleetType: String,
    val mapBearing: Double
)