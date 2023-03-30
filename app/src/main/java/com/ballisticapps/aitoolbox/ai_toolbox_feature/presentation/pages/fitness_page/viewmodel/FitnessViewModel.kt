package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.fitness_page.viewmodel

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
class FitnessViewModel @Inject constructor(
    private val getOpenAITextResponse: GetOpenAITextResponse,
    private val sharedRepository: SharedRepository,
    private val adHelper: AdHelper
) : ViewModel() {
    val metricSystemExpanded = mutableStateOf(false)
    val metricSystems = listOf("Imperial", "Metric")
    val metricType = mutableStateOf("Imperial")

    // Height and weight properties for both metric and imperial systems
    val heightMetricText = mutableStateOf("")
    val heightFeetImperialText = mutableStateOf("")
    val heightInchImperialText = mutableStateOf("")
    val weightMetricText = mutableStateOf("")
    val weightImperialText = mutableStateOf("")


    val experienceLevelExpanded = mutableStateOf(false)
    val workoutFrequencyExpanded = mutableStateOf(false)
    val workoutTypeExpanded = mutableStateOf(false)
    val fitnessGoalExpanded = mutableStateOf(false)
    val promptExpanded = mutableStateOf(false)

    val experienceLevels = listOf("Beginner", "Intermediate", "Advanced")
    val workoutFrequencies = listOf("0 times per week", "1-2 times per week", "3-4 times per week", "5-6 times per week", "Every day")
    val workoutTypes = listOf("Strength Training", "Cardio", "Yoga", "Pilates", "HIIT", "Circuit Training", "CrossFit")
    val fitnessGoals = listOf("Lose Weight", "Build Muscle", "Improve Cardio", "Increase Flexibility", "Improve Endurance", "Boost Strength")
    val promptTypes = listOf("Workout Plan", "Exercise Recommendations", "Stretching Exercises", "Warm-up and Cool-down Routines", "Injury Prevention", "Progress Tracking", "Goal Setting", "Motivation Techniques", "Fitness Equipment Recommendations", "Hydration and Nutrition Tips")

    // Initialize the daysOfWeek list with mutable states for each day
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday").map {
        DaySelection(it, mutableStateOf(false))
    }

    fun getSelectedDays(): List<String> {
        return daysOfWeek.filter { it.isSelected.value }.map { it.day }
    }

    val ageText = mutableStateOf("")
    val selectedExperienceLevelText = mutableStateOf("")
    val selectedWorkoutFrequencyText = mutableStateOf("")
    val selectedWorkoutTypeText = mutableStateOf("")
    val selectedFitnessGoalText = mutableStateOf("")
    val selectedPromptText = mutableStateOf("Workout Plan")
    val detailsText = mutableStateOf("")

    private fun showAd(activity: ComponentActivity) {
        getAIResponse()
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
            val prompt =
                "Generate a workout plan for $selectedDays based on ${selectedWorkoutTypeText.value} workouts. " +
                        "The experience level is ${selectedExperienceLevelText.value}, " +
                        "the desired workout frequency is ${selectedWorkoutFrequencyText.value}, " +
                        "and the fitness goal is ${selectedFitnessGoalText.value}. " +
                        "Age: ${ageText.value}, Metric type: ${metricType.value}, Height: ${
                            if (metricType.value == "Imperial") {
                                "${heightFeetImperialText.value} feet and ${heightInchImperialText.value} inches"
                            } else {
                                heightMetricText.value + " cm"
                            }
                        }, " +
                        "Weight: ${
                            if (metricType.value == "Imperial") {
                                weightImperialText.value + " lbs"
                            } else {
                                weightMetricText.value + " kg"
                            }
                        }, Details: ${detailsText.value}. " +
                        "I need help with ${selectedPromptText.value}."

            val result = getOpenAITextResponse(
                Prompt(
                    model = "gpt-3.5-turbo",
                    messages = listOf(
                        Message(
                            role = "user",
                            content = prompt
                        )
                    ),
                    maxTokens = 1500,
                    temperature = 0.7,
                    topP = 1.0,
                    presencePenalty = 0,
                    frequencyPenalty = 0,
                    user = "Fitness helper"
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
                        Log.d("FitnessViewModel", "Error: ${result.message}")
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
