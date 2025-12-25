package com.valimade.geofinder.di.sigma.flp

import com.google.android.gms.location.FusedLocationProviderClient
import com.valimade.geofinder.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@FLPLocationScope
@Subcomponent(modules = [FLPLocationModule::class])
interface FLPLocationComponent {

    fun inject(activity: MainActivity)

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun fusedClient(fusedClient: FusedLocationProviderClient): Builder

        fun build(): FLPLocationComponent
    }
}