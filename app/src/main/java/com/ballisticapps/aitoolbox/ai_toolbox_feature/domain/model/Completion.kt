package com.ballisticapps.aitoolbox.ai_toolbox_feature.domain.model

import com.ballisticapps.aitoolbox.ai_toolbox_feature.data.remote.dto.Choice
import com.ballisticapps.aitoolbox.ai_toolbox_feature.data.remote.dto.Usage

data class Completion(
    val choices: List<Choice>,
    val created: Int,
    val id: String,
    val model: String,
    val `object`: String,
    val usage: Usage
)