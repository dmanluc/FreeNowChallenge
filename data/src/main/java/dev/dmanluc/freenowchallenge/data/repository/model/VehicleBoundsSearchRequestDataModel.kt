package dev.dmanluc.freenowchallenge.data.repository.model

data class VehicleBoundsSearchRequestDataModel(
    val firstBoundCoordinates: Pair<Double, Double>,
    val secondBoundCoordinates: Pair<Double, Double>
)