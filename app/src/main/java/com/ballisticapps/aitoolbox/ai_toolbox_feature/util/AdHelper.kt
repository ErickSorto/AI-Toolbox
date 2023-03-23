package com.ballisticapps.aitoolbox.ai_toolbox_feature.util

import androidx.activity.ComponentActivity
import com.ballisticapps.aitoolbox.reward_ads_feature.domain.repository.AdManagerRepository
import com.ballisticapps.aitoolbox.reward_ads_feature.domain.repository.AdCallback
import com.google.android.gms.ads.rewarded.RewardItem
import javax.inject.Inject

class AdHelper @Inject constructor(private val adManagerRepository: AdManagerRepository) {

    fun showAd(activity: ComponentActivity, onAdRewarded: () -> Unit, onAdFailedToLoad: () -> Unit) {
        activity.runOnUiThread {
            adManagerRepository.loadRewardedAd(activity) {
                adManagerRepository.showRewardedAd(
                    activity,
                    object : AdCallback {
                        override fun onAdLoaded() {
                            TODO("Not yet implemented")
                        }

                        override fun onAdFailedToLoad(errorCode: Int) {
                            onAdFailedToLoad()
                        }

                        override fun onAdOpened() {
                            TODO("Not yet implemented")
                        }

                        override fun onAdClosed() {

                        }

                        override fun onAdRewarded(reward: RewardItem) {
                            onAdRewarded()
                        }

                        override fun onAdLeftApplication() {
                            TODO("Not yet implemented")
                        }
                    }
                )
            }
        }
    }
}
