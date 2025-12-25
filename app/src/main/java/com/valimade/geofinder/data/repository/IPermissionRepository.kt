package com.valimade.geofinder.data.repository

import io.reactivex.Single

interface IPermissionRepository {
    fun getLocationPermission(): Boolean
}