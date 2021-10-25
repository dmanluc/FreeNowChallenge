package dev.dmanluc.freenowchallenge.data.datasource.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VehiclePoisApiModel(
    @Json(name = "poiList")
    val vehiclePoiList: List<VehiclePoiApiModel> = emptyList()
)

@JsonClass(generateAdapter = true)
data class VehiclePoiApiModel(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "coordinate")
    val coordinate: VehiclePoiCoordinateApiModel?,
    @Json(name = "fleetType")
    val fleetType: String?,
    @Json(name = "heading")
    val heading: Double?
)

@JsonClass(generateAdapter = true)
data class VehiclePoiCoordinateApiModel(
    @Json(name = "latitude")
    val latitude: Double?,
    @Json(name = "longitude")
    val longitude: Double?
)
