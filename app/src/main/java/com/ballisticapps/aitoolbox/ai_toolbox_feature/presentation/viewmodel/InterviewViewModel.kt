//package com.ballisticapps.interviewai.interview_ai_feature.presentation.viewmodel

//@HiltViewModel
//class InterviewViewModel @Inject constructor(
//    private val adManagerRepository: AdManagerRepository,
//    private val getOpenAITextResponse: GetOpenAITextResponse,
//    savedStateHandle: SavedStateHandle
//) : ViewModel() {
//    var helperState = mutableStateOf(HelperUIState())
//        private set
//
//    val _helperState = helperState.value
//
//
//    fun onEvent(event: HelperEvent) {
//        when (event) {
//            is HelperEvent.ClickGenerateResponseButton -> {
//                //make ad request
//                event.activity.runOnUiThread {
//                    adManagerRepository.loadRewardedAd(event.activity) {
//                        //show ad
//                        adManagerRepository.showRewardedAd(
//                            event.activity,
//                            object : AdCallback {
//                                override fun onAdClosed() {
//                                    //to be added later
//                                }
//
//                                override fun onAdRewarded(reward: RewardItem) {
//                                    getAIResponse()
//                                }
//
//                                override fun onAdLeftApplication() {
//                                    TODO("Not yet implemented")
//                                }
//
//                                override fun onAdLoaded() {
//                                    TODO("Not yet implemented")
//                                }
//
//                                override fun onAdFailedToLoad(errorCode: Int) {
//                                    //to be added later
//                                }
//
//                                override fun onAdOpened() {
//                                    TODO("Not yet implemented")
//                                }
//                            })
//                    }
//                }
//            }
//            is HelperEvent.ClickRecordQuestionButton -> {
//                //to be added later
//            }
//            is HelperEvent.EnteredPrompt -> {
//                helperState.value = helperState.value.copy(question = event.value)
//            }
//        }
//    }
//
//    private fun getAIResponse() {
//        viewModelScope.launch {
//            val result = getOpenAITextResponse(
//                Prompt(
//                    model = "gpt-3.5-turbo",
//                    messages = listOf(
//                        Message(
//                            role = "user",
//                            content = "Answer the following in the form of a wise and creative poem: " + helperState.value.question + "?"
//                        )
//                    ),
//                    maxTokens = 256,
//                    temperature = 0.7,
//                    topP = 1.0,
//                    presencePenalty = 0,
//                    frequencyPenalty = 0,
//                    user = "Poem seeker"
//                )
//            )
//
//            result.collect { result ->
//                when (result) {
//                    is Resource.Success -> {
//                        result.data as ChatCompletion
//
//                        helperState.value = helperState.value.copy(
//                            answer = result.data.choices[0].message.content,
//                            questionAIExplanation = helperState.value.questionAIExplanation.copy(
//                                response = result.data.choices[0].message.content,
//                                isLoading = false
//                            )
//                        )
//                    }
//                    is Resource.Error -> {
//                        Log.d("HelperViewModel", "Error: ${result.message}")
//                    }
//                    is Resource.Loading -> {
//                        //is loading is true
//                        helperState.value = helperState.value.copy(
//                            questionAIExplanation = helperState.value.questionAIExplanation.copy(
//                                isLoading = true
//                            )
//                        )
//                        Log.d("HelperViewModel", "Loading")
//                    }
//                }
//            }
//        }
//    }
//}

data class HelperUIState(
    val question: String = "",
    val answer: String? = "",
    val isAnswerVisible: Boolean = false,
    val questionAIExplanation: QuestionAIExplanation = QuestionAIExplanation(
        response = "",
        isLoading = false,
        error = null
    )
) {
}

data class QuestionAIExplanation(
    val response: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)

