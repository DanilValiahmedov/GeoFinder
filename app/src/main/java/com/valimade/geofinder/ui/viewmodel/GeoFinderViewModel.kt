package com.valimade.geofinder.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valimade.geofinder.domain.exception.PermissionNotGrantedException
import com.valimade.geofinder.domain.usecase.IGetAccurateLocationUseCase
import com.valimade.geofinder.domain.usecase.IGetFLPLocationUseCase
import com.valimade.geofinder.domain.usecase.IGetLocationPermissionUseCase
import com.valimade.geofinder.ui.model.GeoState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class GeoFinderViewModel  @Inject constructor(
    private val getLocationPermissionUseCase: IGetLocationPermissionUseCase,
    private val getFLPLocationUseCase: IGetFLPLocationUseCase,
    private val getAccurateLocationUseCase: IGetAccurateLocationUseCase,
): ViewModel(), ViewModelProvider.Factory {
    private val _state = MutableStateFlow(GeoState())
    val state = _state.asStateFlow()

    init{
        _state.update {
            it.copy(
                isPermission = getLocationPermissionUseCase.getPermission(),
            )
        }
    }

    fun getPermission(isPermission: Boolean) {
        _state.update {
            it.copy(
                isPermission = isPermission,
            )
        }
    }

    fun onFLPClick() {
        getFLPLocationUseCase
            .getLocation()
            .doOnSubscribe {
                _state.update {
                    it.copy(
                        flpLocation = "Процесс запущен. Ожидайте",
                    )
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ location ->
                _state.update {
                    it.copy(
                        flpLocation = "latitude: ${location.latitude} \n" +
                                "longitude: ${location.longitude} \n" +
                                "accuracy: ${location.accuracy} \n" +
                                "method: ${location.method}",
                    )
                }
            }, { error ->
                if (error == PermissionNotGrantedException()) {
                    _state.update {
                        it.copy(
                            isPermission = false,
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            flpLocation = "error: ${error.message}",
                        )
                    }
                }

            })
    }

    fun onLocationManagerClick() {
        getAccurateLocationUseCase
            .getLocation()
            .doOnSubscribe {
                _state.update {
                    it.copy(
                        locationManagerLocation = "Процесс запущен. Ожидайте",
                    )
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ location ->
                _state.update {
                    it.copy(
                        locationManagerLocation = "latitude: ${location.latitude} \n" +
                                "longitude: ${location.longitude} \n" +
                                "accuracy: ${location.accuracy} \n" +
                                "method: ${location.method}",

                    )
                }
            }, { error ->
                if (error == PermissionNotGrantedException()) {
                    _state.update {
                        it.copy(
                            isPermission = false,
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            locationManagerLocation = "error: ${error.message}",
                        )
                    }
                }
            })
    }

}