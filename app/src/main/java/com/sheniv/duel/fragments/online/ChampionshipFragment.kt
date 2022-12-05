package com.sheniv.duel.fragments.online

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sheniv.duel.R
import com.sheniv.duel.adapters.FragmentStateAdapter
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentChampionshipBinding
import com.sheniv.duel.extantion.AppValueEventListener
import com.sheniv.duel.firebase.*
import com.sheniv.duel.models.AllGames
import com.sheniv.duel.models.UserFirebase

class ChampionshipFragment : BaseFragment<FragmentChampionshipBinding>() {

    private val topFragment = listOf<Fragment>(
        OnlineStopwatchRatingFragment(),
        OnlineTimerRatingFragment()
    )

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentChampionshipBinding.inflate(inflater, container, false)

    override fun FragmentChampionshipBinding.onBindView(savedInstanceState: Bundle?) {

        checkUser()

        exit.setOnClickListener {
            exitDialog()
        }

        val tabLayout = requireView().findViewById<TabLayout>(R.id.tab_layout)

        viewPager2.adapter = FragmentStateAdapter(requireActivity(), topFragment)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            val games = AllGames().onlineGames()
            tab.setIcon(games[position].icon)
            tab.setText(games[position].name)
        }.attach()

    }

    private fun checkUser() {
        val firebaseUser = AUTH.currentUser
        if (firebaseUser == null) {
            navController.navigate(R.id.fragment_registration)
        } else {
            initUserFirebase()
        }
    }

    private fun initUserFirebase() {
        CURRENT_UID = AUTH.currentUser?.uid.toString()
        Log.e("current", "$CURRENT_UID")
        REF_DATABASE_ROOT.child(NODE_USER).child(CURRENT_UID)
            .addListenerForSingleValueEvent(AppValueEventListener {
                USER_FIREBASE = it.getValue(UserFirebase::class.java) ?: UserFirebase()
            })

    }

    private fun exitDialog() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(getString(R.string.sign_out))
            .setMessage(getString(R.string.alert_sign_out))
            .setPositiveButton("OK") { dialog, _ ->
                AUTH.signOut()
                navController.navigate(R.id.navigation_championship)
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
}