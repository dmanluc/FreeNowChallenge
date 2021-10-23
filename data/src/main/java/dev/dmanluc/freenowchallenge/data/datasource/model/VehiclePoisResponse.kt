package dev.dmanluc.freenowchallenge.data.datasource.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VehiclePoisResponse(
    @Json(name = "poiList")
    val vehiclePoiList: List<VehiclePoi> = emptyList()
)

@JsonClass(generateAdapter = true)
data class VehiclePoi(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "coordinate")
    val coordinate: VehiclePoiCoordinate?,
    @Json(name = "fleetType")
    val fleetType: String?,
    @Json(name = "heading")
    val heading: Double?
)

@JsonClass(generateAdapter = true)
data class VehiclePoiCoordinate(
    @Json(name = "latitude")
    val latitude: Double?,
    @Json(name = "longitude")
    val longitude: Double?
)
