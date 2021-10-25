package dev.dmanluc.freenowchallenge.data.mappers

import dev.dmanluc.freenowchallenge.data.datasource.model.VehiclePoisRequestApiModel
import dev.dmanluc.freenowchallenge.data.repository.model.VehicleBoundsSearchRequestDataModel

fun VehicleBoundsSearchRequestDataModel.toApi() = VehiclePoisRequestApiModel(
    firstBoundCoordinates.first,
    firstBoundCoordinates.second,
    secondBoundCoordinates.first,
    secondBoundCoordinates.second
)
