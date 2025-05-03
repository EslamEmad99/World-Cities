package com.example.domain.usecase

import com.example.domain.repository.CityRepository
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(private val repository: CityRepository) {

    suspend operator fun invoke(query: String) = repository.searchCities(query)
}
