package com.valimade.geofinder.di.sigma.flp

import com.google.android.gms.location.FusedLocationProviderClient
import com.valimade.geofinder.data.repository.FLPRepository
import com.valimade.geofinder.data.repository.IFLPRepository
import com.valimade.geofinder.domain.usecase.GetFLPLocationUseCase
import com.valimade.geofinder.domain.usecase.IGetFLPLocationUseCase
import com.valimade.geofinder.domain.usecase.IGetLocationPermissionUseCase
import dagger.Module
import dagger.Provides

@Module
class FLPLocationModule {

    @FLPLocationScope
    @Provides
    fun provideFLPRepository(
        fusedClient: FusedLocationProviderClient
    ): IFLPRepository {
        return FLPRepository(fusedClient)
    }

    @FLPLocationScope
    @Provides
    fun provideGetFLPLocationUseCase(
        fLPRepository: IFLPRepository,
        getLocationPermissionUseCase: IGetLocationPermissionUseCase
    ): IGetFLPLocationUseCase {
        return GetFLPLocationUseCase(fLPRepository, getLocationPermissionUseCase)
    }

}