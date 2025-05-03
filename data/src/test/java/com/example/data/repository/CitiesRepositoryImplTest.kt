package com.example.data.repository

import com.example.data.datasource.CitiesDataSource
import com.example.data.model.CityDto
import com.example.data.model.CoordinateDataDto
import com.example.domain.model.City
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CitiesRepositoryImplTest {

    private lateinit var citiesDataSource: CitiesDataSource
    private lateinit var citiesRepository: CitiesRepositoryImpl

    private val testCities = listOf(
        CityDto(707860, "Hurzuf", "UA", CoordinateDataDto(34.283333, 44.549999)),
        CityDto(519188, "Novinki", "RU", CoordinateDataDto(37.666668, 55.683334)),
        CityDto(1283378, "Gorkhā", "NP", CoordinateDataDto(84.633331, 28.0)),
        CityDto(1283379, "Gorakhpur", "NP", CoordinateDataDto(84.633332, 29.0)),
        CityDto(1283380, "Goa", "IN", CoordinateDataDto(73.7480, 15.2993))
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        citiesDataSource = mockk()
        runBlocking {
            coEvery { citiesDataSource.getAllCities() } returns testCities
        }
        citiesRepository = CitiesRepositoryImpl(citiesDataSource)
    }

    @Test
    fun `searchCities with prefix G returns cities sorted and paginated`() = runTest {
        val result = citiesRepository.searchCities("G", offset = 0, limit = 10)

        val expected = listOf(
            City(1283380, "Goa", "IN", 15.2993, 73.7480),
            City(1283378, "Gorkhā", "NP", 28.0, 84.633331),
            City(1283379, "Gorakhpur", "NP", 29.0, 84.633332)
        ).sortedWith(compareBy({ it.name.lowercase() }, { it.country.lowercase() }))

        assertEquals(expected, result)
    }

    @Test
    fun `searchCities with prefix H returns only Hurzuf`() = runTest {
        val result = citiesRepository.searchCities("H", offset = 0, limit = 10)

        assertEquals(1, result.size)
        assertEquals("Hurzuf", result[0].name)
    }

    @Test
    fun `searchCities pagination returns correct subset`() = runTest {
        val resultPage1 = citiesRepository.searchCities("G", offset = 0, limit = 2)
        val resultPage2 = citiesRepository.searchCities("G", offset = 2, limit = 2)

        assertEquals(2, resultPage1.size)
        assertEquals(1, resultPage2.size)
    }
}
