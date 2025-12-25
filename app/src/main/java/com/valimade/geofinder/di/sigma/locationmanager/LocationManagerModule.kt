package com.valimade.geofinder.di.sigma.locationmanager

import android.location.LocationManager
import com.valimade.geofinder.data.repository.ILocationManagerRepository
import com.valimade.geofinder.data.repository.LocationManagerRepository
import com.valimade.geofinder.domain.usecase.GetAccurateLocationUseCase
import com.valimade.geofinder.domain.usecase.IGetLocationPermissionUseCase
import com.valimade.geofinder.domain.usecase.IGetAccurateLocationUseCase
import dagger.Module
import dagger.Provides

@Module
class LocationManagerModule {

    @LocationManagerScope
    @Provides
    fun provideLocationManagerRepository(
        locationManager: LocationManager
    ): ILocationManagerRepository {
        return LocationManagerRepository(locationManager)
    }

    @LocationManagerScope
    @Provides
    fun provideGetAccurateLocationUseCase(
        locationManagerRepository: ILocationManagerRepository,
        getLocationPermissionUseCase: IGetLocationPermissionUseCase
    ): IGetAccurateLocationUseCase {
        return GetAccurateLocationUseCase(locationManagerRepository, getLocationPermissionUseCase)
    }

}