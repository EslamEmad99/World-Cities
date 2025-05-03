package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityDto(
    @Json(name = "_id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "country")
    val country: String,

    @Json(name = "coord")
    val coordinate: CoordinateDataDto
)

@JsonClass(generateAdapter = true)
data class CoordinateDataDto(
    @Json(name = "lon")
    val longitude: Double,

    @Json(name = "lat")
    val latitude: Double
)