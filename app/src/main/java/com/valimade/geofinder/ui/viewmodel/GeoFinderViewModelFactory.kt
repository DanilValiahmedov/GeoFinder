package com.valimade.geofinder.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valimade.geofinder.domain.usecase.IGetAccurateLocationUseCase
import com.valimade.geofinder.domain.usecase.IGetFLPLocationUseCase
import com.valimade.geofinder.domain.usecase.IGetLocationPermissionUseCase
import javax.inject.Inject

class GeoFinderViewModelFactory @Inject constructor(
    private val getLocationPermissionUseCase: IGetLocationPermissionUseCase,
    private val getFLPLocationUseCase: IGetFLPLocationUseCase,
    private val getAccurateLocationUseCase: IGetAccurateLocationUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GeoFinderViewModel(
            getLocationPermissionUseCase,
            getFLPLocationUseCase,
            getAccurateLocationUseCase,
        ) as T
    }
}