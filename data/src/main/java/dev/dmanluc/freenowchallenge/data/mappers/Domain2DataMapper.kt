package dev.dmanluc.freenowchallenge.data.mappers

import dev.dmanluc.freenowchallenge.data.repository.model.VehicleBoundsSearchRequestDataModel
import dev.dmanluc.freenowchallenge.domain.model.MapBounds

fun MapBounds.toDataModel(): VehicleBoundsSearchRequestDataModel = VehicleBoundsSearchRequestDataModel(
    Pair(bounds.first.latLng.first, bounds.first.latLng.second),
    Pair(bounds.second.latLng.first, bounds.second.latLng.second)
)
