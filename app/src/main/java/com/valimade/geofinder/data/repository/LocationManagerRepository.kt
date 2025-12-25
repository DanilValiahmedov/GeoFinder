package com.valimade.geofinder.data.repository

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.valimade.geofinder.domain.exception.LocationUnavailableException
import com.valimade.geofinder.domain.model.GeoLocation
import com.valimade.geofinder.domain.model.LocationResult
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationManagerRepository @Inject constructor(
    private val locationManager: LocationManager
) : ILocationManagerRepository {

    override fun getAccurateLocation(): Single<GeoLocation> {
        return Single.zip(
            getProviderLocation(LocationManager.GPS_PROVIDER),
            getProviderLocation(LocationManager.NETWORK_PROVIDER)
        ) { gps, network ->

            when {
                gps is LocationResult.Empty && network is LocationResult.Empty -> {
                    throw LocationUnavailableException("Не удалось определить геолокацию")
                }

                gps is LocationResult.Value && network is LocationResult.Empty -> {
                    gps.location
                }

                gps is LocationResult.Empty && network is LocationResult.Value -> {
                    network.location
                }

                gps is LocationResult.Value && network is LocationResult.Value -> {
                    val chosen = if (gps.location.accuracy <= network.location.accuracy) {
                        gps.location
                    } else {
                        network.location
                    }
                    chosen
                }

                else -> {
                    throw LocationUnavailableException("Не удалось определить геолокацию")
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getProviderLocation(provider: String): Single<LocationResult> {
        return Maybe.create<GeoLocation> { emitter ->
            val listener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(
                            GeoLocation(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                accuracy = location.accuracy,
                                method = provider,
                            )
                        )
                    }
                }
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }

            locationManager.requestSingleUpdate(provider, listener, Looper.getMainLooper())

        }
            .timeout(5, TimeUnit.SECONDS)
            .map<LocationResult> { LocationResult.Value(it) }
            .switchIfEmpty(Single.just(LocationResult.Empty))
            .onErrorReturnItem(LocationResult.Empty)
            .doOnError { LocationResult.Empty }
    }

}