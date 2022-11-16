package com.sheniv.duel.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.PurchaseInfo
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sheniv.duel.R
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentHelpBinding
import com.sheniv.duel.extantion.AD_UNIT_ID
import com.sheniv.duel.extantion.showToast

class HelpFragment : BaseFragment<FragmentHelpBinding>(), BillingProcessor.IBillingHandler {

    private var mIsLoading = false
    private var mRewardedAd: RewardedAd? = null
    private var bp: BillingProcessor? = null
    private val BILLING_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl+lkimxHE0kx3DNyf2xrmc5FCeWQvsSJHOjATS9UCDQVeQcsu08CnRj7jhNPKOFEXlLJ7lUYT2IpxEyfWPbQLF8mW9IEMNe9BoSP7G8VuIbd++gGCBsHe+U9+MZnMZmbPUiadMLte4ZORwpvQhx5l/sPfiCDuyyluVOYcWP4cYFixoIlsoFkRn3q4+2ZIGtovsrJ9tNdXjsUlS4DtmR5/1EFGgVzM5o+bmp+L8gt0GNxQ3v91QIaz+/cackF/P3SXUAdy5sB2dTbwG0/4atxTqE7kc0BDSBp/17UILHIW/wejayFLcrRIxX/b9rb10lJyMmBSLKKKWCKVl8lNFivZQIDAQAB"

    override fun onResume() {
        super.onResume()
        //loadInterAd()
    }


    override fun onDestroyView() {
        bp?.release()
        super.onDestroyView()
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHelpBinding.inflate(inflater, container, false)

    override fun FragmentHelpBinding.onBindView(savedInstanceState: Bundle?) {
        //initAdMob()
        donationsInit()
        val play = "https://play.google.com/store/apps/dev?id=7801316179503456063"
        val play_market = Intent(Intent(Intent.ACTION_VIEW, Uri.parse(play)))

        btnAds.setOnClickListener {
            //showInterAd()
        }

        btnCooperation.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("shekovnikita8@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.cooperation_word))
            }
            startActivity(intent)
        }

        btnPlayMarket.setOnClickListener { startActivity(play_market) }

        btnInfo.setOnClickListener { dialogAboutAd() }

        btnDonation.setOnClickListener {
            bp?.consumePurchaseAsync("donations", object : BillingProcessor.IPurchasesResponseListener{
                override fun onPurchasesSuccess() {

                }

                override fun onPurchasesError() {
                }
            })
            bp?.purchase(requireActivity(), "donations")
        }
    }

    private fun dialogAboutAd() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(getString(R.string.this_is_not_difficult))
            .setMessage(getString(R.string.about_this_is_not_difficult))
            .setPositiveButton("OK") { dialog, _ ->
                binding.btnAds.setBackgroundColor(Color.RED)
                dialog.dismiss()
            }.show()
    }

    /*private fun initAdMob() {
        MobileAds.initialize(requireActivity())
    }

    private fun loadInterAd() {
        val adRequest = AdRequest.Builder().build()

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

    private fun showInterAd() {
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
                        binding.btnAds.background = null
                    }
                }

            mRewardedAd?.show(
                requireActivity()
            ) {
                showContent()
            }
        }
    }*/

    private fun showContent() {
        showToast(getString(R.string.thank_you_for_watching_ads))
    }

    private fun donationsInit() {
        bp = BillingProcessor.newBillingProcessor(requireActivity(), BILLING_KEY, this)
        bp?.initialize()
    }

    override fun onProductPurchased(productId: String, details: PurchaseInfo?) {
    }

    override fun onPurchaseHistoryRestored() {
    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
        showToast("Error ${error?.localizedMessage}")
    }

    override fun onBillingInitialized() {
    }
}