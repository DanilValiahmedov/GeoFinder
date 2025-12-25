package com.valimade.geofinder.di.sigma.locationmanager

import android.location.LocationManager
import com.valimade.geofinder.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@LocationManagerScope
@Subcomponent(modules = [LocationManagerModule::class])
interface LocationManagerComponent {

    fun inject(activity: MainActivity)

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun locationManager(locationManager: LocationManager): Builder

        fun build(): LocationManagerComponent
    }
}