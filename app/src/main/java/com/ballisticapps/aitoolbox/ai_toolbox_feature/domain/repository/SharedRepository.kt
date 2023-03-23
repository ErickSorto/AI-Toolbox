package com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.repository

import HelperUIState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.ai_page.viewmodel.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SharedRepository @Inject constructor(

) {
    private val _helperState = mutableStateOf(HelperUIState())
    val helperState: State<HelperUIState> get() = _helperState

    val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setAIResponse(response: String) {
        _helperState.value = _helperState.value.copy(answer = response)
    }

    fun setLoading(loading: Boolean) {
        _helperState.value = _helperState.value.copy(questionAIExplanation = _helperState.value.questionAIExplanation.copy(isLoading = loading))
    }

    fun clearAIResponse() {
        _helperState.value = _helperState.value.copy(answer = null)
    }
}