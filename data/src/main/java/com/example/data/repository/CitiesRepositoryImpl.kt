package com.example.data.repository

import com.example.data.datasource.CitiesDataSource
import com.example.data.model.toDomain
import com.example.data.util.CitySearchTrie
import com.example.domain.model.City
import com.example.domain.repository.CitiesRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val dataSource: CitiesDataSource
) : CitiesRepository {

    private val trie = CitySearchTrie()
    private var isInitialized = false
    private val lock = Any()

    private fun ensureInitialized() {
        if (!isInitialized) {
            synchronized(lock) {
                if (!isInitialized) {
                    runBlocking {
                        val cities = dataSource.getAllCities()
                        cities.forEach { trie.insert(it) }
                        isInitialized = true
                    }
                }
            }
        }
    }

    override suspend fun searchCities(query: String): List<City> {
        return searchCities(query, offset = 0, limit = 50)
    }

    fun searchCities(query: String, offset: Int, limit: Int): List<City> {
        ensureInitialized()

        val matched = trie.search(query)
            .distinctBy { it.id }
            .sortedWith(compareBy({ it.name.lowercase() }, { it.country.lowercase() }))
            .drop(offset)
            .take(limit)

        return matched.map { it.toDomain() }
    }
}
