package dev.dmanluc.freenowchallenge.data.datasource.remote

import dev.dmanluc.freenowchallenge.data.datasource.model.VehiclePoi
import dev.dmanluc.freenowchallenge.domain.model.MapBounds

interface VehiclesRemoteDataSource {

    suspend fun getVehiclePois(mapBounds: MapBounds): List<VehiclePoi>

}
