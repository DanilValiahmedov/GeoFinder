package com.valimade.geofinder.domain.usecase

import com.valimade.geofinder.data.repository.IFLPRepository
import com.valimade.geofinder.domain.model.GeoLocation
import io.reactivex.Single
import javax.inject.Inject

class GetFLPLocationUseCase @Inject constructor(
    private val fLPRepository: IFLPRepository
): IGetFLPLocationUseCase {
    override fun getLocation(): Single<GeoLocation> {
        return fLPRepository.getFLPLocation()
    }
}