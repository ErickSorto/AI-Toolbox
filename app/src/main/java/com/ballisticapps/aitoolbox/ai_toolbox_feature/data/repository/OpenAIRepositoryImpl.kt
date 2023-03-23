package com.ballisticapps.aitoolbox.ai_toolbox_feature.data.repository


import com.ballisticapps.aitoolbox.core.Resource
import com.ballisticapps.aitoolbox.ai_toolbox_feature.data.remote.OpenAITextApi
import com.ballisticapps.aitoolbox.ai_toolbox_feature.data.remote.dto.toChatCompletion
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model.ChatCompletion
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model.Prompt
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.repository.OpenAIRepository
import javax.inject.Inject

class OpenAIRepositoryImpl @Inject constructor(private val api: OpenAITextApi) : OpenAIRepository {

    override suspend fun getChatCompletion(prompt: Prompt): Resource<ChatCompletion> {

        return try {
            val response = api.getCompletion(prompt)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it.toChatCompletion())
                } ?: Resource.Error("Response body is null")
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}