package com.valimade.geofinder.domain.usecase

import com.valimade.geofinder.data.repository.ILocationManagerRepository
import com.valimade.geofinder.domain.exception.PermissionNotGrantedException
import com.valimade.geofinder.domain.model.GeoLocation
import io.reactivex.Single
import javax.inject.Inject

class GetAccurateLocationUseCase @Inject constructor(
    private val locationManagerRepository: ILocationManagerRepository,
    private val getLocationPermissionUseCase: IGetLocationPermissionUseCase,
): IGetAccurateLocationUseCase {
    override fun getLocation(): Single<GeoLocation> {
        return if (getLocationPermissionUseCase.getPermission()) {
            locationManagerRepository.getAccurateLocation()
        } else {
            Single.error(
                PermissionNotGrantedException("Нет разрешения на получение геолокации")
            )
        }
    }
}