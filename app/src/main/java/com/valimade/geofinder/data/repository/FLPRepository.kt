package com.valimade.geofinder.data.repository

import android.annotation.SuppressLint
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.valimade.geofinder.domain.exception.LocationUnavailableException
import com.valimade.geofinder.domain.model.GeoLocation
import io.reactivex.Single
import javax.inject.Inject

class FLPRepository @Inject constructor(
    private val fusedClient: FusedLocationProviderClient
) : IFLPRepository {

    @SuppressLint("MissingPermission")
    override fun getFLPLocation(): Single<GeoLocation> {
        return Single.create { emitter ->

            val request = CurrentLocationRequest.Builder()
                .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
                .build()

            fusedClient.getCurrentLocation(request, null)
                .addOnSuccessListener { location ->
                    if (location != null) {
                        emitter.onSuccess(
                            GeoLocation(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                accuracy = location.accuracy,
                            )
                        )
                    } else {
                        emitter.onError(
                            LocationUnavailableException("Не удалось определить геолокацию")
                        )
                    }
                }
                .addOnFailureListener { error ->
                    emitter.onError(error)
                }
        }
    }
}
