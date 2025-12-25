package com.valimade.geofinder.di.sigma

import com.valimade.geofinder.di.sigma.flp.FLPLocationComponent
import com.valimade.geofinder.di.sigma.locationmanager.LocationManagerComponent
import dagger.Module

@Module(
    subcomponents = [
        LocationManagerComponent::class,
        FLPLocationComponent::class
    ]
)
class AppModule {

}
