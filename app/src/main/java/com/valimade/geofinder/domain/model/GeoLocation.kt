package com.valimade.geofinder.domain.model

data class GeoLocation(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
)