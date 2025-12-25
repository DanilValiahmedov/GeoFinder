package com.valimade.geofinder.domain.usecase

import com.valimade.geofinder.data.repository.IPermissionRepository

class GetLocationPermissionUseCase(
    private val permissionRepository: IPermissionRepository
): IGetLocationPermissionUseCase {
    override fun getPermission(): Boolean {
        return permissionRepository.getLocationPermission()
    }
}