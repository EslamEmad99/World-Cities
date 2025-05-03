package com.example.data.datasource

import android.content.Context
import com.example.data.model.CityDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class CitiesDataSourceImpl (
    private val context: Context,
    private val moshi: Moshi
):CitiesDataSource {

    override suspend fun getAllCities(): List<CityDto> {
        val json = context.assets.open("cities.json").bufferedReader().use { it.readText() }
        val type = Types.newParameterizedType(List::class.java, CityDto::class.java)
        val adapter = moshi.adapter<List<CityDto>>(type)
        return adapter.fromJson(json).orEmpty()
    }
}