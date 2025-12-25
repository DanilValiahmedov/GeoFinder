package com.valimade.geofinder.di.sigma.locationmanager

import android.location.LocationManager

interface LocationManagerDependencyProvider {

    var locationManagerComponent: LocationManagerComponent?

    fun getLocationManagerComponent(locationManager: LocationManager): LocationManagerComponent {
        if (locationManagerComponent == null) {
            locationManagerComponent = getAppComponent()
                .addLocationManagerComponent()
                .locationManager(locationManager)
                .build()
        }
        return locationManagerComponent!!
    }

    fun releaseLocationManagerComponent() {
        locationManagerComponent = null
    }

    fun getAppComponent(): LocationManagerAppComponent
}
