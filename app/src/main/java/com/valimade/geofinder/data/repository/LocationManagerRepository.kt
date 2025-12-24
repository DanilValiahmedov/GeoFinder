package com.valimade.geofinder.data.repository

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.location.LocationListener
import android.os.Bundle
import com.valimade.geofinder.domain.exception.LocationUnavailableException
import com.valimade.geofinder.domain.model.GeoLocation
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class LocationManagerRepository(
    private val locationManager: LocationManager
) : ILocationManagerRepository {

    override fun getAccurateLocation(): Single<GeoLocation> {
        return Single.zip(
            getProviderLocation(LocationManager.GPS_PROVIDER)
                .timeout(5, TimeUnit.SECONDS)
                .onErrorReturnItem(null),
            getProviderLocation(LocationManager.NETWORK_PROVIDER)
                .timeout(5, TimeUnit.SECONDS)
                .onErrorReturnItem(null)
        ) { gps: GeoLocation?, network: GeoLocation? ->

            when {
                gps == null && network == null -> throw LocationUnavailableException("Не удалось определить геолокацию")
                gps == null -> network
                network == null -> gps
                gps.accuracy <= network.accuracy -> gps
                else -> network
            }!!
        }
    }

    @SuppressLint("MissingPermission")
    private fun getProviderLocation(provider: String): Single<GeoLocation?> {
        return Single.create { emitter ->

            val listener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(
                            GeoLocation(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                accuracy = location.accuracy
                            )
                        )
                    }
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }

            locationManager.requestSingleUpdate(
                provider,
                listener,
                Looper.getMainLooper()
            )
        }
    }

}