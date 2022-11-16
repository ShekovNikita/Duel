package com.sheniv.duel.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.sheniv.duel.R
import com.sheniv.duel.adapters.ClickOnTheGame
import com.sheniv.duel.adapters.GameAdapter
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.database.room.PlayerDatabase
import com.sheniv.duel.databinding.FragmentMainBinding
import com.sheniv.duel.extantion.*
import com.sheniv.duel.models.Game

class MainFragment : BaseFragment<FragmentMainBinding>(), ClickOnTheGame {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    override fun FragmentMainBinding.onBindView(savedInstanceState: Bundle?) {

        bottomNavigationView.beVisible()
        recyclerGames.layoutManager = GridLayoutManager(context, 2)
        recyclerGames.adapter = GameAdapter(this@MainFragment, allGames)
    }

    override fun selectTheGame(game: Game) {
        selectedGame = game
        if (game.name != R.string.soon) navController.navigate(R.id.selectPlayersFragment)
        else gameInfo(game)
    }

    override fun gameInfo(game: Game) {

        var title = ""
        var message = ""

        when (game.name){
            R.string.duel -> {
                title = getString(R.string.duel)
                message = getString(R.string.rules_duel)
            }
            R.string.timer -> {
                title = getString(R.string.timer)
                message = getString(R.string.rules_timer)
            }
            R.string.stopwatch -> {
                title = getString(R.string.stopwatch)
                message = getString(R.string.rules_stopwatch)
            }
            R.string.soon -> {
                title = getString(R.string.soon)
                message = getString(R.string.rules_soon)
            }
        }

        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

}