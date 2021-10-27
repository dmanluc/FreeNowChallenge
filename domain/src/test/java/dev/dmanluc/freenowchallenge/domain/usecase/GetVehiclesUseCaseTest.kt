package dev.dmanluc.freenowchallenge.domain.usecase

import arrow.core.left
import arrow.core.right
import dev.dmanluc.freenowchallenge.domain.model.DomainError
import dev.dmanluc.freenowchallenge.domain.model.MapBounds
import dev.dmanluc.freenowchallenge.domain.model.MapCoordinate
import dev.dmanluc.freenowchallenge.domain.repository.PoiRepository
import dev.dmanluc.freenowchallenge.domain.utils.VehicleModelFactory
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetVehiclesUseCaseTest {

    private lateinit var SUT: GetVehiclesUseCase

    private val repository = mockk<PoiRepository>(relaxed = true)

    @Before
    fun setUp() {
        SUT = GetVehiclesUseCase(repository)
    }

    @Test
    fun `verify get vehicle POIs on happy path interaction`() = runBlockingTest {
        val requestBounds = MapBounds(Pair(MapCoordinate(Pair(50.0, 56.0)), MapCoordinate(Pair(56.0, 50.0))))
        val vehiclePoisModel = VehicleModelFactory.createMany(20)
        coEvery { repository.getVehicles(requestBounds) } returns vehiclePoisModel.right()

        val result = SUT.invoke(GetVehiclesUseCase.Params(requestBounds))

        assertEquals(result.shouldBeRight(), vehiclePoisModel)
        coVerify(exactly = 1) { repository.getVehicles(requestBounds) }
    }

    @Test
    fun `verify get vehicle POIs on http error interaction`() = runBlockingTest {
        val requestBounds = MapBounds(Pair(MapCoordinate(Pair(50.0, 56.0)), MapCoordinate(Pair(56.0, 50.0))))
        val domainError = DomainError.HttpError(400, "Error")
        coEvery { repository.getVehicles(requestBounds) } returns domainError.left()

        val errorResult = SUT.invoke(GetVehiclesUseCase.Params(requestBounds))

        assertEquals(errorResult.shouldBeLeft(), domainError)
        coVerify(exactly = 1) { repository.getVehicles(requestBounds) }
    }

    @Test
    fun `verify get vehicle POIs on network error interaction`() = runBlockingTest {
        val requestBounds = MapBounds(Pair(MapCoordinate(Pair(50.0, 56.0)), MapCoordinate(Pair(56.0, 50.0))))
        val domainError = DomainError.NetworkError(Throwable("Error"))
        coEvery { repository.getVehicles(requestBounds) } returns domainError.left()

        val errorResult = SUT.invoke(GetVehiclesUseCase.Params(requestBounds))

        assertEquals(errorResult.shouldBeLeft(), domainError)
        coVerify(exactly = 1) { repository.getVehicles(requestBounds) }
    }

    @Test
    fun `verify get vehicle POIs on unknown error interaction`() = runBlockingTest {
        val requestBounds = MapBounds(Pair(MapCoordinate(Pair(50.0, 56.0)), MapCoordinate(Pair(56.0, 50.0))))
        val domainError = DomainError.UnknownError(Throwable("Error"))
        coEvery { repository.getVehicles(requestBounds) } returns domainError.left()

        val errorResult = SUT.invoke(GetVehiclesUseCase.Params(requestBounds))

        assertEquals(errorResult.shouldBeLeft(), domainError)
        coVerify(exactly = 1) { repository.getVehicles(requestBounds) }
    }
}