package com.example.domain.repository

import com.example.domain.model.City

interface CitiesRepository {
    suspend fun searchCities(query: String): List<City>
}
