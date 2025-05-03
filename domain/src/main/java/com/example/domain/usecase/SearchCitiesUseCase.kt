package com.example.domain.usecase

import com.example.domain.repository.CitiesRepository
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(private val repository: CitiesRepository) {

    suspend operator fun invoke(query: String) = repository.searchCities(query)
}
