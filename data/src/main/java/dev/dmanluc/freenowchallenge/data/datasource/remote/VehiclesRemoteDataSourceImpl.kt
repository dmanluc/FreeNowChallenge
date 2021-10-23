package dev.dmanluc.freenowchallenge.data.datasource.remote

import dev.dmanluc.freenowchallenge.data.datasource.api.PoiApi
import dev.dmanluc.freenowchallenge.data.datasource.model.VehiclePoi
import dev.dmanluc.freenowchallenge.domain.model.MapBounds
import javax.inject.Inject

class VehiclesRemoteDataSourceImpl @Inject constructor(
    private val api: PoiApi
) : VehiclesRemoteDataSource {

    override suspend fun getVehiclePois(mapBounds: MapBounds): List<VehiclePoi> {
        return api.getVehiclePois(
            mapBounds.bounds.first.latLng.first,
            mapBounds.bounds.first.latLng.second,
            mapBounds.bounds.second.latLng.first,
            mapBounds.bounds.second.latLng.second,
        ).vehiclePoiList
    }

}