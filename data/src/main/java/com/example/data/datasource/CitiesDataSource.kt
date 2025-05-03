package com.example.data.datasource

import com.example.data.model.CityDto

interface CitiesDataSource {

    suspend fun getAllCities(): List<CityDto>
}
