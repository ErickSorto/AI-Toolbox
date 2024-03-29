package com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.usecase

import android.util.Log
import com.ballisticapps.aitoolbox.core.Resource
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model.ChatCompletion
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model.Prompt
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.repository.OpenAIRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import javax.inject.Inject

class GetOpenAITextResponse @Inject constructor(
    private val repository: OpenAIRepository,
) {

    operator fun invoke(prompt: Prompt): Flow<Resource<ChatCompletion>> = flow {
        emit(Resource.Loading())
        val response = repository.getChatCompletion(prompt)
        emit(response)
    }.onStart {
        emit(Resource.Loading())
    }.catch { e ->
        when (e) {
            is HttpException -> {
                emit(Resource.Error("Network Error"))
                Log.e("GetOpenAITextResponse", "Network Error: ${e.message()}")
            }
            else -> {
                emit(Resource.Error("Conversion Error"))
                Log.e("GetOpenAITextResponse", "Conversion Error: ${e.message}")
            }
        }
    }
}