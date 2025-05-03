package com.example.data.model

import com.example.domain.model.City

fun CityDto.toDomain(): City {
    return City(
        id = id,
        name = name,
        country = country,
        latitude = coordinate.latitude,
        longitude = coordinate.longitude
    )
}
