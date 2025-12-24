package com.valimade.geofinder

import android.app.Application
import com.valimade.geofinder.di.AppComponent
import com.valimade.geofinder.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .build()
    }
}