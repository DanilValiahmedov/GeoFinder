package com.valimade.geofinder.di.simply

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.valimade.geofinder.data.repository.FLPRepository
import com.valimade.geofinder.data.repository.IFLPRepository
import com.valimade.geofinder.data.repository.ILocationManagerRepository
import com.valimade.geofinder.data.repository.IPermissionRepository
import com.valimade.geofinder.data.repository.LocationManagerRepository
import com.valimade.geofinder.data.repository.PermissionRepository
import com.valimade.geofinder.domain.usecase.GetAccurateLocationUseCase
import com.valimade.geofinder.domain.usecase.GetFLPLocationUseCase
import com.valimade.geofinder.domain.usecase.GetLocationPermissionUseCase
import com.valimade.geofinder.domain.usecase.IGetAccurateLocationUseCase
import com.valimade.geofinder.domain.usecase.IGetFLPLocationUseCase
import com.valimade.geofinder.domain.usecase.IGetLocationPermissionUseCase
import com.valimade.geofinder.ui.viewmodel.GeoFinderViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    //Permission
    @Provides
    @Singleton
    fun providePermissionRepository(
        context: Context
    ): IPermissionRepository {
        return PermissionRepository(context)
    }

    @Provides
    @Singleton
    fun provideGetLocationPermissionUseCase(
        permissionRepository: IPermissionRepository
    ): IGetLocationPermissionUseCase {
        return GetLocationPermissionUseCase(permissionRepository)
    }

    //Реализацию через Fused Location Provider API
    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
    @Provides
    @Singleton
    fun provideFLPRepository(
        fusedClient: FusedLocationProviderClient
    ): IFLPRepository {
        return FLPRepository(fusedClient)
    }
    @Provides
    @Singleton
    fun provideGetFLPLocationUseCase(
        fLPRepository: IFLPRepository,
        getLocationPermissionUseCase: IGetLocationPermissionUseCase
    ): IGetFLPLocationUseCase {
        return GetFLPLocationUseCase(fLPRepository, getLocationPermissionUseCase)
    }

    //Реализацию через LocationManager
    @Provides
    @Singleton
    fun provideLocationManager(
        context: Context
    ): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    @Provides
    @Singleton
    fun provideLocationManagerRepository(
        locationManager: LocationManager
    ): ILocationManagerRepository {
        return LocationManagerRepository(locationManager)
    }

    @Provides
    @Singleton
    fun provideGetAccurateLocationUseCase(
        locationManagerRepository: ILocationManagerRepository,
        getLocationPermissionUseCase: IGetLocationPermissionUseCase
    ): IGetAccurateLocationUseCase {
        return GetAccurateLocationUseCase(locationManagerRepository, getLocationPermissionUseCase)
    }

    //ViewModel
    @Provides
    fun provideGeoFinderViewModelFactory(
        getLocationPermissionUseCase: IGetLocationPermissionUseCase,
        getFLPLocationUseCase: IGetFLPLocationUseCase,
        getAccurateLocationUseCase: IGetAccurateLocationUseCase,
    ): GeoFinderViewModelFactory {
        return GeoFinderViewModelFactory(getLocationPermissionUseCase, getFLPLocationUseCase, getAccurateLocationUseCase)
    }

}