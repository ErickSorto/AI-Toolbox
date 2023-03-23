package com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model

import com.ballisticapps.aitoolbox.ai_toolbox_feature.data.remote.dto.Message
import com.google.gson.annotations.SerializedName

data class Prompt(
    val model: String,
    val messages: List<Message>,
    val temperature: Double,
    //top_p
    @SerializedName("top_p")
    val topP: Double,
    @SerializedName("max_tokens")
    val maxTokens: Int = 300,
    @SerializedName("presence_penalty")
    val presencePenalty: Int,
    @SerializedName("frequency_penalty")
    val frequencyPenalty: Int,
    val user: String? = null
) {
}