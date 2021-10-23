package dev.dmanluc.freenowchallenge.domain.repository

import dev.dmanluc.freenowchallenge.domain.model.DomainResult
import dev.dmanluc.freenowchallenge.domain.model.MapBounds
import dev.dmanluc.freenowchallenge.domain.model.Vehicle

interface PoiRepository {

    suspend fun getVehicles(mapBounds: MapBounds): DomainResult<List<Vehicle>>

}