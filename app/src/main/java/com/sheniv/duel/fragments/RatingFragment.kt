package com.sheniv.duel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sheniv.duel.R
import com.sheniv.duel.adapters.ClickOnTheGame
import com.sheniv.duel.adapters.GameAdapter
import com.sheniv.duel.adapters.PlayerRatingAdapter
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentRatingBinding
import com.sheniv.duel.extantion.allGames
import com.sheniv.duel.extantion.db
import com.sheniv.duel.models.Game

class RatingFragment : BaseFragment<FragmentRatingBinding>(), ClickOnTheGame {
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRatingBinding.inflate(inflater, container, false)

    override fun FragmentRatingBinding.onBindView(savedInstanceState: Bundle?) {
        recyclerGames.layoutManager = GridLayoutManager(context, 2)
        recyclerGames.adapter = GameAdapter(this@RatingFragment, allGames)
    }

    override fun selectTheGame(game: Game) {
        binding.gameName.setText(game.name)
        binding.recyclerPlayersRating.adapter = PlayerRatingAdapter(game.name, db.getAll())
    }

    override fun gameInfo(game: Game) {

        var title = ""
        var message = ""

        when (game.name){
            R.string.duel -> {
                title = getString(R.string.duel)
                message = getString(R.string.duel_rating)
            }
            R.string.timer -> {
                title = getString(R.string.timer)
                message = getString(R.string.timer_rating)
            }
            R.string.stopwatch -> {
                title = getString(R.string.stopwatch)
                message = getString(R.string.stopwatch_rating)
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