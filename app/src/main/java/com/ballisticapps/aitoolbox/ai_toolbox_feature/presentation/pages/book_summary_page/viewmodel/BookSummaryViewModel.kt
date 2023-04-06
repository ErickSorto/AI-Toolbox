package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.book_summary_page.viewmodel

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ballisticapps.aitoolbox.ai_toolbox_feature.data.remote.dto.Message
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model.ChatCompletion
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model.Prompt
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.repository.SharedRepository
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.usecase.GetOpenAITextResponse
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.HelperEvent
import com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.pages.ai_page.viewmodel.UiEvent
import com.ballisticapps.aitoolbox.ai_toolbox_feature.util.AdHelper
import com.ballisticapps.aitoolbox.core.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSummaryViewModel @Inject constructor(
    private val getOpenAITextResponse: GetOpenAITextResponse,
    private val sharedRepository: SharedRepository,
    private val adHelper: AdHelper
) : ViewModel() {

    val bookTitleText = mutableStateOf("")
    val authorNameText = mutableStateOf("")
    val chapterText = mutableStateOf("")
    val promptExpanded = mutableStateOf(false)

    val promptTypes = listOf(
        "Summary",
        "Main Ideas",
        "Character Analysis",
        "Themes",
        "Symbols",
        "Important Quotes",
        "Writing Style",
        "Context",
        "Author's Background"
    )

    val selectedPromptText = mutableStateOf("Summary")


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
            is HelperEvent.ClickGenerateResponseWithoutAdButton -> {
                getAIResponse(isFree = true)
            }
        }
    }

    private fun getAIResponse(isFree: Boolean = false) {
        viewModelScope.launch {
            val prompt = buildString {
                append("I need a ${selectedPromptText.value} for the book \"${bookTitleText.value}\"")
                append(" by ${authorNameText.value}")
                if (chapterText.value.isNotEmpty()) {
                    append(", chapter ${chapterText.value}")
                }
                append(".")
            }

            // Customize the API call according to your needs

            val result = getOpenAITextResponse(
                Prompt(
                    model = "gpt-3.5-turbo",  //gpt-4  //gpt-3.5-turbo
                    messages = listOf(
                        Message(
                            role = "user",
                            content = prompt
                        )
                    ),
                    maxTokens = if (isFree) 200 else 1000,
                    temperature = 0.7,
                    topP = 1.0,
                    presencePenalty = 0,
                    frequencyPenalty = 0,
                    user = "Book Summarizer",
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
                        Log.d("BookSummaryViewModel", "Error: ${result.message}")
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