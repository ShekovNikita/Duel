package com.sheniv.duel.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentHelpBinding
import com.sheniv.duel.extantion.showToast

const val AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"

class HelpFragment : BaseFragment<FragmentHelpBinding>() {

    private var interAd: InterstitialAd? = null
    private var mIsLoading = false
    private var mRewardedAd: RewardedAd? = null

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInterAd()
    }

    override fun onPause() {
        super.onPause()
        binding.adView.pause()
    }

    override fun onDestroyView() {
        binding.adView.destroy()
        super.onDestroyView()
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHelpBinding.inflate(inflater, container, false)

    override fun FragmentHelpBinding.onBindView(savedInstanceState: Bundle?) {
        initAdMob()
        val play = "https://play.google.com/store/apps/dev?id=7801316179503456063"
        val play_market = Intent(Intent(Intent.ACTION_VIEW, Uri.parse(play)))

        btnAds.setOnClickListener {
            showInterAd()
        }

        btnCooperation.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("shekovnikita8@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Сотрудничество")
            }
            startActivity(intent)
        }

        btnPlayMarket.setOnClickListener { startActivity(play_market) }
    }

    private fun initAdMob() {
        MobileAds.initialize(requireActivity())
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun loadInterAd(){
        var adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            requireActivity(),
            AD_UNIT_ID,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mIsLoading = false
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd
                    mIsLoading = false
                }
            }
        )
    }

    private fun showInterAd(){
        if (mRewardedAd != null) {
            mRewardedAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        mRewardedAd = null
                        loadInterAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        mRewardedAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
                        // Called when ad is dismissed.
                        showContent()
                    }
                }

            mRewardedAd?.show(
                requireActivity()
            ) {
                showContent()
            }
        }
    }

    private fun showContent(){
        showToast("Спасибо за просмотр рекламы")
    }
}