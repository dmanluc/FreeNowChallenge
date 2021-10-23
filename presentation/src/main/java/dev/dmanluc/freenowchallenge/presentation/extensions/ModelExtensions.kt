package dev.dmanluc.freenowchallenge.presentation.extensions

import com.google.android.gms.maps.model.LatLng
import dev.dmanluc.freenowchallenge.domain.model.FleetType
import dev.dmanluc.freenowchallenge.domain.model.Vehicle
import dev.dmanluc.freenowchallenge.presentation.R
import dev.dmanluc.freenowchallenge.presentation.model.VehicleItem

fun Vehicle.getMapLocation(): LatLng =
    LatLng(this.mapCoordinate.latLng.first, this.mapCoordinate.latLng.second)

fun Vehicle.toUiModel(): VehicleItem {
    val iconResource = when (this.fleetType) {
        FleetType.TAXI -> R.drawable.ic_taxi
        else -> R.drawable.ic_cab
    }

    return VehicleItem(
        this.id,
        this.getMapLocation(),
        this.fleetType,
        iconResource,
        this.mapBearing
    )
}