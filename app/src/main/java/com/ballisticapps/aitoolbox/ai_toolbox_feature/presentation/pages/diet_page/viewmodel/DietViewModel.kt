package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.diet_page.viewmodel

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.MutableState
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
class DietViewModel @Inject constructor(
    private val getOpenAITextResponse: GetOpenAITextResponse,
    private val sharedRepository: SharedRepository,
    private val adHelper: AdHelper
) : ViewModel() {
    val expandedDietType = mutableStateOf(false)
    val metricsExpanded = mutableStateOf(false)
    val goalExpanded = mutableStateOf(false)
    val promptExpanded = mutableStateOf(false)
    val acivityLevelExpanded = mutableStateOf(false)
    val isGenderExpanded = mutableStateOf(false)

    val goalTypes = listOf("Lose Weight", "Gain Weight", "Maintain Weight")
    val activityLevels = listOf(
        "Sedentary (0 hours)",
        "Lightly Active (1-3 hours)",
        "Moderately Active (3-5 hours)",
        "Very Active (6-7 hours)",
        "Extra Active (8+ hours)"
    )

    val genderList = listOf(
        "Male",
        "Female"
    )


    val dietTypes = listOf("Keto", "Paleo", "Mediterranean", "Vegan")
    val metrics = listOf("Imperial", "Metric")

    // Initialize the daysOfWeek list with mutable states for each day
    val daysOfWeek =
        listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday").map {
            DaySelection(it, mutableStateOf(false))
        }
    val promptTypes = listOf(
        "Diet Plan", "List of Foods", "Shopping List", "Diet Advice",
        "Weight Loss Tips",
        "Healthy Eating Habits",
        "Dietary Supplements Recommendations",
        "Overcoming Cravings",
        "Dealing with Emotional Eating",
        "Portion Control Strategies",
        "Balancing Macronutrients",
        "Understanding Food Labels",
        "Boosting Metabolism",
    )


    fun getSelectedDays(): List<String> {
        return daysOfWeek.filter { it.isSelected.value }.map { it.day }
    }

    val selectedGoalText = mutableStateOf("")
    val selectedPromptText = mutableStateOf("Diet Plan")
    var ageText = mutableStateOf("")
    var systemType = mutableStateOf("Imperial")
    var heightMetricText = mutableStateOf("")
    var heightFeetImperialText = mutableStateOf("")
    var heightInchImperialText = mutableStateOf("")
    var weightMetricText = mutableStateOf("")
    var weightImperialText = mutableStateOf("")
    var detailsText = mutableStateOf("")
    val selectedDietTypeText = mutableStateOf("")
    val selectedActivityLevelText = mutableStateOf("")
    val selectedGenderText = mutableStateOf("")


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
            val selectedDays = getSelectedDays().joinToString(separator = ", ")
            val prompt = buildString {
                if (selectedPromptText.value == "Diet Plan") {
                    append("Generate a ${selectedDietTypeText.value} diet plan for $selectedDays and add calorie amount next to each food.")
                } else {
                    append("The diet type chosen is ${selectedDietTypeText.value} ")
                }
                append(" with the goal of ${selectedGoalText.value}, ")
                append("Activity level: ${selectedActivityLevelText.value}, ")
                append("Age: ${ageText.value}, Metric type: ${systemType.value}, Height: ")

                if (systemType.value == "Imperial") {
                    append("${heightFeetImperialText.value}feet and ${heightInchImperialText.value} inches")
                } else {
                    append("${heightMetricText.value}cm")
                }

                append(", Weight: ")

                if (systemType.value == "Imperial") {
                    append("${weightImperialText.value}lbs")
                } else {
                    append("${weightMetricText.value}kg")
                }

                append(", Gender: ${selectedGenderText.value}")

                append(", Details: ${detailsText.value}. ")
                append("I need help with ${selectedPromptText.value}.")
            }

            val result = getOpenAITextResponse(
                Prompt(
                    model = "gpt-3.5-turbo",
                    messages = listOf(
                        Message(
                            role = "user",
                            content = prompt
                        )
                    ),
                    maxTokens = 2000,
                    temperature = 0.7,
                    topP = 1.0,
                    presencePenalty = 0,
                    frequencyPenalty = 0,
                    user = "Diet helper"
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
                        Log.d("DietViewModel", "Error: ${result.message}")
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

data class DaySelection(val day: String, val isSelected: MutableState<Boolean>)