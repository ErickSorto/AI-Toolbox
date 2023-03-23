package com.ballisticapps.aitoolbox.ai_toolbox_feature.data.remote.dto

data class Usage(
    val promptTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int
)