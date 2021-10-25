package dev.dmanluc.freenowchallenge.data.datasource.model

data class VehicleDataModel(
    val id: Int,
    val mapCoordinate: Pair<Double, Double>,
    val fleetType: String,
    val mapBearing: Double
)