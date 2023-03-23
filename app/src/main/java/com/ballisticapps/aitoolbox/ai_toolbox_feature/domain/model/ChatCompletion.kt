package com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model

import com.ballisticapps.aitoolbox.ai_toolbox_feature.data.remote.dto.Choice
import com.ballisticapps.aitoolbox.ai_toolbox_feature.data.remote.dto.Usage
import com.google.gson.annotations.SerializedName

data class ChatCompletion(
    val id: String,
    @SerializedName("object")
    val objectValue: String,
    val created: Int,
    val choices: List<Choice>,
    val usage: Usage
)