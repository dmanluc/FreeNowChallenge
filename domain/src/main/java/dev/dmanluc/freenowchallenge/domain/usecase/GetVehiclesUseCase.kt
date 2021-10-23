package dev.dmanluc.freenowchallenge.domain.usecase

import dev.dmanluc.freenowchallenge.domain.model.DomainResult
import dev.dmanluc.freenowchallenge.domain.model.MapBounds
import dev.dmanluc.freenowchallenge.domain.model.Vehicle
import dev.dmanluc.freenowchallenge.domain.repository.PoiRepository
import javax.inject.Inject

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Use case to fetch vehicle data.
 *
 */
class GetVehiclesUseCase @Inject constructor(private val repository: PoiRepository) {

    data class Params(
        val mapBounds: MapBounds
    )

    suspend operator fun invoke(
        params: Params
    ): DomainResult<List<Vehicle>> {
        return repository.getVehicles(params.mapBounds)
    }

}