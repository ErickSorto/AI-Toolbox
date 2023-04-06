package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.socialmedia_page.viewmodel

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
class SocialMediaViewModel @Inject constructor(
    private val getOpenAITextResponse: GetOpenAITextResponse,
    private val sharedRepository: SharedRepository,
    private val adHelper: AdHelper
) : ViewModel() {
    val expandedSocialMedia = mutableStateOf(false)
    val expandedPrompt = mutableStateOf(false)

    val socialMediaPlatforms = listOf("Twitter", "Instagram", "YouTube", "TikTok", "Facebook")

    val promptOptions = listOf(
        "Generate content ideas",
        "Generate titles",
        "Generate descriptions",
        "Generate captions",
        "Generate hashtags",
        "Generate video ideas",
        "Generate thumbnail ideas",
        "Generate engagement strategies",
        "Generate posting schedules",
        "Generate growth strategies",
        "Generate collaboration ideas",
        "Generate audience targeting strategies"
    )

    val selectedSocialMedia = mutableStateOf("")
    val selectedPrompt = mutableStateOf("")
    val topicOrSubject = mutableStateOf("")

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
        fun onEvent(event: HelperEvent) {
            when (event) {
                is HelperEvent.ClickGenerateResponseButton -> {
                    showAd(event.activity as ComponentActivity)
                }
                is HelperEvent.ClickGenerateResponseWithoutAdButton -> {
                    getAIResponse(isFree = true)
                }
            }
        }
    }

    private fun getAIResponse(isFree: Boolean = false) {
        viewModelScope.launch {
            // Customize the prompt according to your needs
            val prompt =
                "I need help with my ${selectedSocialMedia.value} account. " +
                        "Please provide some ${selectedPrompt.value} related to ${topicOrSubject.value}."

            val result = getOpenAITextResponse(
                Prompt(
                    model = "gpt-3.5-turbo",
                    messages = listOf(
                        Message(
                            role = "user",
                            content = prompt
                        )
                    ),
                    maxTokens = if (isFree) 200 else 500,
                    temperature = 0.7,
                    topP = 1.0,
                    presencePenalty = 0,
                    frequencyPenalty = 0,
                    user = "Social media helper"
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
                        Log.d("SocialMediaViewModel", "Error: ${result.message}")
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