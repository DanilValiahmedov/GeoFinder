package com.valimade.geofinder.di

import com.valimade.geofinder.di.flp.FLPLocationAppComponent
import com.valimade.geofinder.di.flp.FLPLocationComponent
import com.valimade.geofinder.di.locationmanager.LocationManagerAppComponent
import com.valimade.geofinder.di.locationmanager.LocationManagerComponent
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent :
    FLPLocationAppComponent,
    LocationManagerAppComponent {
    override fun addFLPLocationComponent(): FLPLocationComponent.Builder
    override fun addLocationManagerComponent(): LocationManagerComponent.Builder
}