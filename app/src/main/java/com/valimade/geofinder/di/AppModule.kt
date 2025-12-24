package com.valimade.geofinder.di

import com.valimade.geofinder.di.flp.FLPLocationComponent
import com.valimade.geofinder.di.locationmanager.LocationManagerComponent
import dagger.Module

@Module(
    subcomponents = [
        LocationManagerComponent::class,
        FLPLocationComponent::class
    ]
)
class AppModule {

}
