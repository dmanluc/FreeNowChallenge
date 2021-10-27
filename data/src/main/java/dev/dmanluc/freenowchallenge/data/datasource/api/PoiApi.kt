package dev.dmanluc.freenowchallenge.data.datasource.api

import dev.dmanluc.freenowchallenge.data.datasource.model.VehiclePoisApiModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Vehicle POIs API definition
 *
 */
interface PoiApi {

    @GET("/")
    suspend fun getVehiclePois(
        @Query("p1Lat") firstBoundLatitude: Double,
        @Query("p1Lon") firstBoundLongitude: Double,
        @Query("p2Lat") lastBoundLatitude: Double,
        @Query("p2Lon") lastBoundLongitude: Double
    ): VehiclePoisApiModel

}