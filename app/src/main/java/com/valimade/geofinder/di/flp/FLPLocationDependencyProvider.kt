package com.valimade.geofinder.di.flp

import com.google.android.gms.location.FusedLocationProviderClient

interface FLPLocationDependencyProvider {

    var flpLocationComponent: FLPLocationComponent?

    fun getFLPLocationComponent(fusedClient: FusedLocationProviderClient): FLPLocationComponent {
        if (flpLocationComponent == null) {
            flpLocationComponent = getAppComponent()
                .addFLPLocationComponent()
                .fusedClient(fusedClient)
                .build()
        }
        return flpLocationComponent!!
    }

    fun releaseFLPLocationComponent() {
        flpLocationComponent = null
    }

    fun getAppComponent(): FLPLocationAppComponent
}
