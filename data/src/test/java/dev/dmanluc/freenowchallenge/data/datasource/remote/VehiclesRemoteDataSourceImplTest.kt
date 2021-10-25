package dev.dmanluc.freenowchallenge.data.datasource.remote

import dev.dmanluc.freenowchallenge.data.datasource.api.PoiApi
import dev.dmanluc.freenowchallenge.data.datasource.model.VehiclePoisApiModel
import dev.dmanluc.freenowchallenge.data.mappers.toData
import dev.dmanluc.freenowchallenge.data.repository.model.VehicleBoundsSearchRequestDataModel
import dev.dmanluc.freenowchallenge.data.utils.VehiclePoiApiModelFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class VehiclesRemoteDataSourceImplTest {

    private lateinit var SUT: VehiclesRemoteDataSourceImpl

    private val poiApi = mockk<PoiApi>()

    @Before
    fun setUp() {
        SUT = VehiclesRemoteDataSourceImpl(poiApi)
    }

    @Test
    fun `verify get vehicle POIs on happy path interaction`() = runBlockingTest {
        val requestBounds = Pair(50.0, 56.0)
        val vehiclePoisApiModel = VehiclePoisApiModel(VehiclePoiApiModelFactory.createMany(20))
        coEvery { poiApi.getVehiclePois(any(), any(), any(), any()) } returns vehiclePoisApiModel

        val result = SUT.getVehiclePois(VehicleBoundsSearchRequestDataModel(requestBounds, requestBounds))

        assertEquals(vehiclePoisApiModel.toData(), result)
        coVerify(exactly = 1) { poiApi.getVehiclePois(any(), any(), any(), any()) }
    }

    @Test
    fun `verify get vehicle POIs on error interaction`() = runBlockingTest {
        val requestBounds = Pair(50.0, 56.0)
        val errorException = Throwable()
        coEvery { poiApi.getVehiclePois(any(), any(), any(), any()) } throws errorException

        var errorResult: Throwable? = null
        try {
            SUT.getVehiclePois(VehicleBoundsSearchRequestDataModel(requestBounds, requestBounds))
        } catch (t: Throwable) {
            errorResult = t
        }

        assertEquals(errorException, errorResult)
        coVerify(exactly = 1) { poiApi.getVehiclePois(any(), any(), any(), any()) }
    }
}