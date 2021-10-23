package dev.dmanluc.freenowchallenge.presentation.model

import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.LatLng
import dev.dmanluc.freenowchallenge.domain.model.FleetType

data class VehicleItem(
    val id: Int,
    val mapLatLng: LatLng,
    val fleetType: FleetType,
    @DrawableRes val iconResource: Int? = null,
    val mapBearing: Double = 0.0
)