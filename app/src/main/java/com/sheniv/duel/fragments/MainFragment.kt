package com.sheniv.duel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
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
    }

}