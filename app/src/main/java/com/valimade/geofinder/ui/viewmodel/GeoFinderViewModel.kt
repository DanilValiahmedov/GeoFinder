package com.valimade.geofinder.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.valimade.geofinder.domain.usecase.IGetAccurateLocationUseCase
import com.valimade.geofinder.domain.usecase.IGetFLPLocationUseCase
import com.valimade.geofinder.ui.model.GeoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class GeoFinderViewModel  @Inject constructor(
    private val getFLPLocationUseCase: IGetFLPLocationUseCase,
    private val getAccurateLocationUseCase: IGetAccurateLocationUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(GeoState())
    val state = _state.asStateFlow()
}