package com.valimade.geofinder.domain.usecase

import com.valimade.geofinder.data.repository.ILocationManagerRepository
import com.valimade.geofinder.domain.model.GeoLocation
import io.reactivex.Single

class GetAccurateLocationUseCase(
    private val locationManagerRepository: ILocationManagerRepository
): IGetAccurateLocationUseCase {
    override fun getLocation(): Single<GeoLocation> {
        return locationManagerRepository.getAccurateLocation()
    }
}