package com.ballisticapps.aitoolbox.ai_toolbox_feature.data.remote


import com.ballisticapps.aitoolbox.BuildConfig
import com.ballisticapps.aitoolbox.ai_toolbox_feature.data.remote.dto.CompletionDTO
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model.Prompt
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAITextApi {
    @Headers("Content-Type: application/json", "Authorization: Bearer " + BuildConfig.API_KEY)
    @POST("completions")
    suspend fun getCompletion(@Body prompt: Prompt): Response<CompletionDTO>
}