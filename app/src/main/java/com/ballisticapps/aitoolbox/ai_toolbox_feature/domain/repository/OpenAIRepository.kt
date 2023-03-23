package com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.repository

import com.ballisticapps.aitoolbox.core.Resource
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model.ChatCompletion
import com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model.Prompt

interface OpenAIRepository {

    suspend fun getChatCompletion(prompt: Prompt): Resource<ChatCompletion>

}