package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation

import android.app.Activity

sealed class HelperEvent {
    data class ClickGenerateResponseButton(val activity: Activity) : HelperEvent()
    data class ClickGenerateResponseWithoutAdButton(val activity: Activity) : HelperEvent()
}