package dev.dmanluc.freenowchallenge.presentation.extensions

import com.google.android.gms.maps.model.LatLng
import dev.dmanluc.freenowchallenge.domain.model.Vehicle

fun Vehicle.getMapLocation(): LatLng = LatLng(this.mapCoordinate.latLng.first, this.mapCoordinate.latLng.second)