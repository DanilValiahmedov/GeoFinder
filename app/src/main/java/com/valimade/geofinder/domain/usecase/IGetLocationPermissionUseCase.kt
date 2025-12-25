package com.valimade.geofinder.domain.usecase

import io.reactivex.Single

interface IGetLocationPermissionUseCase {
    fun getPermission(): Boolean
}