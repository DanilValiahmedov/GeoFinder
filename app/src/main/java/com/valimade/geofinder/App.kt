package com.valimade.geofinder

import android.app.Application
import com.valimade.geofinder.di.simply.AppComponent
import com.valimade.geofinder.di.simply.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory()
            .create(this)
    }
}