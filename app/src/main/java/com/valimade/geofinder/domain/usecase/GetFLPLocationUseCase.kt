package com.valimade.geofinder.domain.usecase

import com.valimade.geofinder.data.repository.IFLPRepository
import com.valimade.geofinder.domain.model.GeoLocation
import io.reactivex.Single

class GetFLPLocationUseCase(
    private val fLPRepository: IFLPRepository
): IGetFLPLocationUseCase {

    override fun getLocation(): Single<GeoLocation> {
        return fLPRepository.getFLPLocation()
    }
}