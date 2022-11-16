package com.sheniv.duel.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import com.sheniv.duel.R
import com.sheniv.duel.adapters.SelectPlayersAdapter
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentSelectPlayersBinding
import com.sheniv.duel.extantion.*

class SelectPlayersFragment : BaseFragment<FragmentSelectPlayersBinding>() {
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSelectPlayersBinding.inflate(inflater, container, false)

    override fun FragmentSelectPlayersBinding.onBindView(savedInstanceState: Bundle?) {
        bottomNavigationView.beGone()
        recyclerPlayers.adapter = SelectPlayersAdapter(db.getAll())

        btnContinue.setOnClickListener {
            if(selectedPlayers.size > 1) {
                if (selectedGame.name == R.string.duel && selectedPlayers.size != 2) {
                    showToast(getString(R.string.duel_must_have_2_players))
                } else {
                    when (selectedGame.name) {
                        R.string.stopwatch -> navController.navigate(R.id.stopWatchGameFragment)
                        R.string.duel -> navController.navigate(R.id.duelGameFragment)
                        R.string.timer -> navController.navigate(R.id.timerGameFragment)
                    }
                }
            } else {
                showToast(getString(R.string.minimum_2_players_can_play))
            }
        }
    }
}