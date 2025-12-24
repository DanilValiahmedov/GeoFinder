package com.valimade.geofinder.di

import com.valimade.geofinder.di.flp.FLPLocationModule
import com.valimade.geofinder.di.locationmanager.LocationManagerModule
import dagger.Module

@Module(
    includes = [
        FLPLocationModule::class,
        LocationManagerModule::class,
    ]
)
class AppModule {

}
