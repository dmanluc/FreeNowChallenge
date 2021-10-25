package dev.dmanluc.freenowchallenge.data.datasource.remote

import dev.dmanluc.freenowchallenge.data.datasource.api.PoiApi
import dev.dmanluc.freenowchallenge.data.datasource.model.VehicleDataModel
import dev.dmanluc.freenowchallenge.data.mappers.toApi
import dev.dmanluc.freenowchallenge.data.mappers.toData
import dev.dmanluc.freenowchallenge.data.repository.model.VehicleBoundsSearchRequestDataModel
import javax.inject.Inject

class VehiclesRemoteDataSourceImpl @Inject constructor(
    private val api: PoiApi
) : VehiclesRemoteDataSource {

    override suspend fun getVehiclePois(mapBounds: VehicleBoundsSearchRequestDataModel): List<VehicleDataModel> {
        return api.getVehiclePois(
            mapBounds.toApi().p1Lat,
            mapBounds.toApi().p1Lon,
            mapBounds.toApi().p2Lat,
            mapBounds.toApi().p2Lon
        ).toData()
    }

}