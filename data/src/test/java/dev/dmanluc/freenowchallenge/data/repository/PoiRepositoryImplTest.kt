package dev.dmanluc.freenowchallenge.data.repository

import dev.dmanluc.freenowchallenge.data.datasource.model.VehiclePoisApiModel
import dev.dmanluc.freenowchallenge.data.datasource.remote.VehiclesRemoteDataSource
import dev.dmanluc.freenowchallenge.data.mappers.toData
import dev.dmanluc.freenowchallenge.data.mappers.toDomainModel
import dev.dmanluc.freenowchallenge.data.utils.VehiclePoiApiModelFactory
import dev.dmanluc.freenowchallenge.domain.model.MapBounds
import dev.dmanluc.freenowchallenge.domain.model.MapCoordinate
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PoiRepositoryImplTest {

    private lateinit var SUT: PoiRepositoryImpl

    private val remoteDataSource = mockk<VehiclesRemoteDataSource>()

    @Before
    fun setUp() {
        SUT = PoiRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `verify get vehicle POIs on happy path interaction`() = runBlockingTest {
        val requestBounds = MapBounds(Pair(MapCoordinate(Pair(50.0, 56.0)), MapCoordinate(Pair(56.0, 50.0))))
        val vehiclePoisApiModel = VehiclePoisApiModel(VehiclePoiApiModelFactory.createMany(20))
        coEvery { remoteDataSource.getVehiclePois(any()) } returns vehiclePoisApiModel.toData()

        val result = SUT.getVehicles(requestBounds)

        assertEquals(result.shouldBeRight(), vehiclePoisApiModel.toData().map { it.toDomainModel() })
        coVerify(exactly = 1) { remoteDataSource.getVehiclePois(any()) }
    }

    @Test
    fun `verify get vehicle POIs on error interaction`() = runBlockingTest {
        val requestBounds = MapBounds(Pair(MapCoordinate(Pair(50.0, 56.0)), MapCoordinate(Pair(56.0, 50.0))))
        val errorException = Throwable()
        coEvery { remoteDataSource.getVehiclePois(any()) } throws errorException

        val errorResult = SUT.getVehicles(requestBounds)

        assertEquals(errorResult.shouldBeLeft(), errorException)
        coVerify(exactly = 1) { remoteDataSource.getVehiclePois(any()) }
    }
}