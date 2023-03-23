package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.financial_page.viewmodel

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
class FinanceViewModel @Inject constructor(
    private val getOpenAITextResponse: GetOpenAITextResponse,
    private val sharedRepository: SharedRepository,
    private val adHelper: AdHelper
) : ViewModel() {
    val expandedFinanceGoal = mutableStateOf(false)
    val promptExpanded = mutableStateOf(false)

    val salaryAmountText = mutableStateOf("")
    val billCostsText = mutableStateOf("")
    val foodCostsText = mutableStateOf("")
    val totalExpensesText = mutableStateOf("")

    val financeGoals = listOf(
        "Saving Money",
        "Building Wealth",
        "Investing",
        "Budgeting",
        "Paying Off Debt",
        "Retirement Planning",
        "Financial Independence",
        "Other"
    )

    val promptTypes = listOf(
        "Money Saving Tips",
        "Wealth Building Strategies",
        "Investment Options",
        "Budgeting Techniques",
        "Debt Payoff Methods",
        "Retirement Savings Advice",
        "Steps to Financial Independence",
        "Managing Financial Risk",
        "Tax Planning",
    )

    val selectedFinanceGoalText = mutableStateOf("")
    val financeDetailsText = mutableStateOf("")
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
                "I have a salary of $${salaryAmountText.value} per month, and my monthly expenses are: " +
                        "Bills: $${billCostsText.value}, Food: $${foodCostsText.value}, and Other: $${totalExpensesText.value}. " +
                        "My financial goal is ${selectedFinanceGoalText.value}. I need help with ${selectedPromptText.value}. " +
                        "Additional details: ${financeDetailsText.value}."

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
                    user = "Finance helper"
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
                        Log.d("FinanceViewModel", "Error: ${result.message}")
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