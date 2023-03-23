package com.ballisticapps.aitoolbox.ai_toolbox_feature.presentation.components

import android.app.Activity
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAd(adUnitId: String) {
    val context = LocalContext.current
    val density = LocalDensity.current

    AndroidView(
        factory = {
            AdView(context).apply {
                this.adUnitId = adUnitId
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        update = { adView ->
            adView.apply {
                val adSize = AdSize.BANNER
                setAdSize(adSize)
                val params = ViewGroup.LayoutParams(
                    (adSize.getWidthInPixels(context) / density.density).toInt(),
                    (adSize.getHeightInPixels(context) / density.density).toInt()
                )
                layoutParams = params
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}


