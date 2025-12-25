package com.valimade.geofinder.di.sigma

import com.valimade.geofinder.di.sigma.flp.FLPLocationAppComponent
import com.valimade.geofinder.di.sigma.flp.FLPLocationComponent
import com.valimade.geofinder.di.sigma.locationmanager.LocationManagerAppComponent
import com.valimade.geofinder.di.sigma.locationmanager.LocationManagerComponent
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent :
    FLPLocationAppComponent,
    LocationManagerAppComponent {
    override fun addFLPLocationComponent(): FLPLocationComponent.Builder
    override fun addLocationManagerComponent(): LocationManagerComponent.Builder
}