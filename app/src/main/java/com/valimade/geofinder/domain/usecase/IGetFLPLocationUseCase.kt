package com.valimade.geofinder.domain.usecase

import com.valimade.geofinder.domain.model.GeoLocation
import io.reactivex.Single

interface IGetFLPLocationUseCase {
    fun getLocation(): Single<GeoLocation>
}