package com.valimade.geofinder.domain.model

sealed interface LocationResult {
    data class Value(val location: GeoLocation) : LocationResult
    data object Empty : LocationResult
}