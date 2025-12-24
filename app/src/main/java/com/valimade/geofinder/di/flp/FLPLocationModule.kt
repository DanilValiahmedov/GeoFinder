package com.valimade.geofinder.di.flp

import com.google.android.gms.location.FusedLocationProviderClient
import com.valimade.geofinder.data.repository.FLPRepository
import com.valimade.geofinder.data.repository.IFLPRepository
import com.valimade.geofinder.domain.usecase.GetFLPLocationUseCase
import com.valimade.geofinder.domain.usecase.IGetFLPLocationUseCase
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
        flpRepository: IFLPRepository
    ): IGetFLPLocationUseCase {
        return GetFLPLocationUseCase(flpRepository)
    }

}