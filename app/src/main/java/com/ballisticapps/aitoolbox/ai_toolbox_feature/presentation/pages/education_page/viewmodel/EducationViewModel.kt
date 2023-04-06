package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.education_page.viewmodel

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
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.diet_page.viewmodel.DaySelection
import com.ballisticapps.aitoolbox.ai_toolbox_feature.util.AdHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EducationViewModel @Inject constructor(
    private val getOpenAITextResponse: GetOpenAITextResponse,
    private val sharedRepository: SharedRepository,
    private val adHelper: AdHelper
) : ViewModel() {
    val subjectOfStudy = mutableStateOf("")
    val experienceLevel = mutableStateOf("")
    val studyGoalText = mutableStateOf("")

    val expandedExperienceLevel = mutableStateOf(false)
    val experienceLevels = listOf("Beginner", "Intermediate", "Advanced")

    val expandedPromptType = mutableStateOf(false)
    val promptTypes = listOf(
        "Study Plan",
        "Daily Study Routines",
        "Study Tips",
        "Roadmap for Subject",
        "Time Management Techniques",
        "Memory Improvement Techniques",
        "Effective Note-taking Strategies"
    )

    val selectedPromptText = mutableStateOf("Study Plan")

    // Initialize the daysOfWeek list with mutable states for each day
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday").map {
        DaySelection(it, mutableStateOf(false))
    }

    fun getSelectedDays(): List<String> {
        return daysOfWeek.filter { it.isSelected.value }.map { it.day }
    }

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
            is HelperEvent.ClickGenerateResponseWithoutAdButton -> {
                getAIResponse(isFree = true)
            }
        }
    }

    private fun getAIResponse(isFree: Boolean = false) {
        viewModelScope.launch {
            // Customize the prompt according to your needs
            val selectedDays = getSelectedDays().joinToString(separator = ", ")
            val prompt =
                "I want to study ${subjectOfStudy.value} with an experience level of ${experienceLevel.value}. " +
                        "My study goal is ${studyGoalText.value}. " +
                        "I need help with ${selectedPromptText.value}. " +
                        "I plan to study on the following days: ${selectedDays}."

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
                    user = "Education helper"
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
                        Log.d("EducationViewModel", "Error: ${result.message}")
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