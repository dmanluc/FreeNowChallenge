package dev.dmanluc.freenowchallenge.data.datasource.remote

import dev.dmanluc.freenowchallenge.data.datasource.model.VehicleDataModel
import dev.dmanluc.freenowchallenge.data.repository.model.VehicleBoundsSearchRequestDataModel

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Definition of a remote data source to fetch POI data
 *
 */
interface VehiclesRemoteDataSource {

    suspend fun getVehiclePois(mapBounds: VehicleBoundsSearchRequestDataModel): List<VehicleDataModel>

}
