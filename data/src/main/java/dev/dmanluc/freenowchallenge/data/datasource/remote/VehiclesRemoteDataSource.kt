package dev.dmanluc.freenowchallenge.data.datasource.remote

import dev.dmanluc.freenowchallenge.data.datasource.model.VehicleDataModel
import dev.dmanluc.freenowchallenge.data.repository.model.VehicleBoundsSearchRequestDataModel

interface VehiclesRemoteDataSource {

    suspend fun getVehiclePois(mapBounds: VehicleBoundsSearchRequestDataModel): List<VehicleDataModel>

}
