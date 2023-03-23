package com.ballisticapps.aitoolbox.ai_toolbox_feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Choice(
    val index : Int,
    val message: Message,
    @SerializedName("finish_reason")
    val finishReason: String
)