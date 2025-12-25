package com.valimade.geofinder.ui.model

data class GeoState(
    val isPermission: Boolean = false,
    val flpLocation: String = "",
    val locationManagerLocation: String = "",
)