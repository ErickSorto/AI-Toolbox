package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.interview_page.viewmodel

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
class InterviewViewModel @Inject constructor(
    private val getOpenAITextResponse: GetOpenAITextResponse,
    private val sharedRepository: SharedRepository,
    private val adHelper: AdHelper
) : ViewModel() {
    val expandedLevel = mutableStateOf(false)
    val expandedJobType = mutableStateOf(false)
    val expandedSpecificJob = mutableStateOf(false)

    val jobLevels =
        listOf("Beginner", "Intermediate", "Advanced", "Intern", "Junior", "Mid Level", "Senior")
    val jobTypes = listOf("Full Time", "Part Time", "Contract", "Internship", "Temporary")
    val specificJobs = listOf(
        "Software Engineer",
        "Software Developer",
        "Designer",
        "Data Scientist",
        "Banker",
        "Accountant",
        "Lawyer",
        "Doctor",
        "Nurse",
        "Teacher",
        "Engineer",
        "Architect",
        "Scientist",
        "Researcher",
        "Sales",
        "Marketing",
        "Business",
        "Manager",
        "Executive"
    )

    val selectedLevelText = mutableStateOf("")
    var selectedJobTypeText = mutableStateOf("")
    var selectedSpecificJobText = mutableStateOf("")
    var skillsText = mutableStateOf("")


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
                "I have an upcoming interview for a ${selectedLevelText.value} ${selectedSpecificJobText.value} " +
                        "position (${selectedJobTypeText.value}). My skills include ${skillsText.value}. " +
                        "Please provide some interview tips and possible questions I might be asked."

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
                    user = "Interview helper"
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
                        Log.d("InterviewViewModel", "Error: ${result.message}")
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