package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.addiction_page.viewmodel

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ballisticapps.aitoolbox.core.Resource
import com.ballisticapps.aitoolbox.ai_toolbox_feature.data.remote.dto.Message
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model.ChatCompletion
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model.Prompt
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.repository.SharedRepository
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.usecase.GetOpenAITextResponse
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.HelperEvent
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.ai_page.viewmodel.UiEvent
import com.ballisticapps.aitoolbox.ai_toolbox_feature.util.AdHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddictionViewModel @Inject constructor(
    // private val sharedAIViewModel: SharedAIViewModel,
    private val getOpenAITextResponse: GetOpenAITextResponse,
    private val sharedRepository: SharedRepository,
    private val adHelper: AdHelper,
) : ViewModel() {

    val expandedAddictionType = mutableStateOf(false)
    val promptExpanded = mutableStateOf(false)

    val addictionTypes = listOf(
        "Smoking",
        "Alcohol",
        "Drugs",
        "Sugar",
        "Caffeine",
        "Screen Time",
        "Social Media",
        "Gambling",
        "Other"
    )

    val promptTypes = listOf(
        "Trigger to Watch Out For List",
        "Habits to Build to Prevent",
        "List of Online Resources",
        "Strategies for Overcoming Cravings",
        "Tips for Managing Stress",
        "Social Support and Accountability",
        "Alternative Coping Mechanisms",
        "Tracking and Monitoring Progress",
    )

    val selectedAddictionTypeText = mutableStateOf("")
    val addictionDetailsText = mutableStateOf("")
    val selectedPromptText = mutableStateOf(promptTypes.first())


    private fun showAd(activity: ComponentActivity) {
        adHelper.showAd(activity,
            onAdRewarded = {
                getAIResponse()
                viewModelScope.launch {
                    sharedRepository._eventFlow.emit(UiEvent.ShowSnackBar("Thank you for supporting me!"))
                }
            },
            onAdFailedToLoad = {
                viewModelScope.launch {
                    sharedRepository._eventFlow.emit(UiEvent.ShowSnackBar("Ad failed to load. Please try again."))
                }
            }
        )
    }

    fun onEvent(event: HelperEvent) {
        when (event) {
            is HelperEvent.ClickGenerateResponseButton -> {
                showAd(event.activity as ComponentActivity)
            }
        }
    }

    private fun getAIResponse() {
        viewModelScope.launch {
            // Customize the prompt according to your needs
            val prompt =
                "Give me advice for overcoming ${selectedAddictionTypeText.value} addiction. Details: ${addictionDetailsText.value}. I need help with ${selectedPromptText.value}."
            val result = getOpenAITextResponse(
                Prompt(
                    model = "gpt-3.5-turbo",
                    messages = listOf(
                        Message(
                            role = "user",
                            content = prompt
                        )
                    ),
                    maxTokens = 500,
                    temperature = 0.7,
                    topP = 1.0,
                    presencePenalty = 0,
                    frequencyPenalty = 0,
                    user = "Addiction helper"
                )
            )

            result.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data as ChatCompletion
                        sharedRepository.setAIResponse(result.data.choices[0].message.content)
                        sharedRepository.setLoading(false)
                    }
                    is Resource.Error -> {
                        Log.d("AddictionViewModel", "Error: ${result.message}")
                        sharedRepository.setLoading(false)
                    }
                    is Resource.Loading -> {
                        sharedRepository.setLoading(true)
                    }
                }
            }
        }
    }
}