package com.sheniv.duel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sheniv.duel.R
import com.sheniv.duel.adapters.GameAdapter
import com.sheniv.duel.adapters.OnlineGameAdapter
import com.sheniv.duel.adapters.OnlineRatingAdapter
import com.sheniv.duel.adapters.PlayerRatingAdapter
import com.sheniv.duel.adapters.interfaces.ClickOnTheGame
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentChampionshipBinding
import com.sheniv.duel.extantion.AppValueEventListener
import com.sheniv.duel.extantion.allGames
import com.sheniv.duel.extantion.db
import com.sheniv.duel.firebase.*
import com.sheniv.duel.models.AllGames
import com.sheniv.duel.models.Game
import com.sheniv.duel.models.UserFirebase

class ChampionshipFragment : BaseFragment<FragmentChampionshipBinding>(), ClickOnTheGame {
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentChampionshipBinding.inflate(inflater, container, false)

    override fun FragmentChampionshipBinding.onBindView(savedInstanceState: Bundle?) {
        checkUser()

        exit.setOnClickListener {
            exitDialog()
        }

        recyclerGames.adapter = OnlineGameAdapter(this@ChampionshipFragment, AllGames().onlineGames())
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

    override fun selectTheGame(game: Game) {
        binding.recyclerPlayersRating.adapter = OnlineRatingAdapter(game.name, db.getAll())
    }

    override fun gameInfo(game: Game) {
        when(game.name){
            R.string.stopwatch -> {}
            R.string.timer -> {}
        }
    }
}