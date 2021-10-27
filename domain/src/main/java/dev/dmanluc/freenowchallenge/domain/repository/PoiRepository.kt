package dev.dmanluc.freenowchallenge.domain.repository

import dev.dmanluc.freenowchallenge.domain.model.DomainResult
import dev.dmanluc.freenowchallenge.domain.model.MapBounds
import dev.dmanluc.freenowchallenge.domain.model.Vehicle

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Definition of a component via repository pattern to handle the source of truth for app data
 *
 */
interface PoiRepository {

    suspend fun getVehicles(mapBounds: MapBounds): DomainResult<List<Vehicle>>

}