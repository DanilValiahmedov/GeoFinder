package com.valimade.geofinder.domain.usecase

import com.valimade.geofinder.data.repository.IFLPRepository
import com.valimade.geofinder.domain.exception.PermissionNotGrantedException
import com.valimade.geofinder.domain.model.GeoLocation
import io.reactivex.Single
import javax.inject.Inject

class GetFLPLocationUseCase @Inject constructor(
    private val fLPRepository: IFLPRepository,
    private val getLocationPermissionUseCase: IGetLocationPermissionUseCase,
): IGetFLPLocationUseCase {
    override fun getLocation(): Single<GeoLocation> {
        return if (getLocationPermissionUseCase.getPermission()) {
            fLPRepository.getFLPLocation()
        } else {
            Single.error(
                PermissionNotGrantedException()
            )
        }
    }
}