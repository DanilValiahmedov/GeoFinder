package com.valimade.geofinder.data.repository

import com.valimade.geofinder.domain.model.GeoLocation
import io.reactivex.Single

interface ILocationManagerRepository {
    fun getAccurateLocation(): Single<GeoLocation>
}