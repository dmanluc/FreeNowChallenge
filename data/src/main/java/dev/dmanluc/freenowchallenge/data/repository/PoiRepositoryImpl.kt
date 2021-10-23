package dev.dmanluc.freenowchallenge.data.repository

import arrow.core.left
import arrow.core.right
import dev.dmanluc.freenowchallenge.data.datasource.remote.VehiclesRemoteDataSource
import dev.dmanluc.freenowchallenge.data.datasource.mapper.toDomainModel
import dev.dmanluc.freenowchallenge.domain.model.DomainResult
import dev.dmanluc.freenowchallenge.domain.model.MapBounds
import dev.dmanluc.freenowchallenge.domain.model.Vehicle
import dev.dmanluc.freenowchallenge.domain.repository.PoiRepository
import javax.inject.Inject

class PoiRepositoryImpl @Inject constructor(
    private val vehiclesRemoteDataSource: VehiclesRemoteDataSource
): PoiRepository {

    override suspend fun getVehicles(mapBounds: MapBounds): DomainResult<List<Vehicle>> {
        return try {
            vehiclesRemoteDataSource.getVehiclePois(mapBounds).map { it.toDomainModel() }.right()
        } catch (t: Throwable) {
            t.left()
        }
    }

}