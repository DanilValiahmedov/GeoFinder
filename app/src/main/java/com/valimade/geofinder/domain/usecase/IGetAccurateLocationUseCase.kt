package com.valimade.geofinder.domain.usecase

import com.valimade.geofinder.domain.model.GeoLocation
import io.reactivex.Single

interface IGetAccurateLocationUseCase {
    fun getLocation(): Single<GeoLocation>
}