package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.ai_page.viewmodel

import HelperUIState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedAIViewModel @Inject constructor(
    private val sharedRepository: SharedRepository
) : ViewModel() {


    val eventFlow = sharedRepository.eventFlow

    val helperState: State<HelperUIState> get() = sharedRepository.helperState

    fun setAIResponse(response: String) {
        sharedRepository.setAIResponse(response)
    }

    fun setLoading(loading: Boolean) {
        sharedRepository.setLoading(loading)
    }

    fun clearAIResponse() {
        sharedRepository.clearAIResponse()
    }
}

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
}