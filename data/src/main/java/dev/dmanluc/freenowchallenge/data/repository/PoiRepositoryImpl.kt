package dev.dmanluc.freenowchallenge.data.repository

import arrow.core.left
import arrow.core.right
import dev.dmanluc.freenowchallenge.data.datasource.remote.VehiclesRemoteDataSource
import dev.dmanluc.freenowchallenge.data.mappers.toDataModel
import dev.dmanluc.freenowchallenge.data.mappers.toDomainModel
import dev.dmanluc.freenowchallenge.domain.model.DomainError
import dev.dmanluc.freenowchallenge.domain.model.DomainResult
import dev.dmanluc.freenowchallenge.domain.model.MapBounds
import dev.dmanluc.freenowchallenge.domain.model.Vehicle
import dev.dmanluc.freenowchallenge.domain.repository.PoiRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PoiRepositoryImpl @Inject constructor(
    private val vehiclesRemoteDataSource: VehiclesRemoteDataSource
): PoiRepository {

    override suspend fun getVehicles(mapBounds: MapBounds): DomainResult<List<Vehicle>> {
        return try {
            vehiclesRemoteDataSource.getVehiclePois(mapBounds.toDataModel()).map { it.toDomainModel() }.right()
        } catch (t: Throwable) {
            handleException(t).left()
        }
    }

    private fun handleException(throwable: Throwable): DomainError {
        return when (throwable) {
            is HttpException -> {
                val code = throwable.code()
                val errorResponseMessage = obtainErrorMessage(throwable)
                DomainError.HttpError(code, errorResponseMessage)
            }
            is IOException -> {
                DomainError.NetworkError(throwable)
            }
            else -> {
                DomainError.UnknownError(throwable)
            }
        }
    }

    private fun obtainErrorMessage(throwable: HttpException) = throwable.response()?.errorBody()?.string().orEmpty()

}