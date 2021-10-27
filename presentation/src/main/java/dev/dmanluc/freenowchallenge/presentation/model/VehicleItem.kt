package dev.dmanluc.freenowchallenge.presentation.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.LatLng
import dev.dmanluc.freenowchallenge.domain.model.FleetType
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehicleItem(
    val id: String,
    val mapLatLng: LatLng,
    val fleetType: FleetType,
    @DrawableRes val iconResource: Int? = null,
    val mapBearing: Double = 0.0
) : Parcelable