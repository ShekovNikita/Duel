package com.sheniv.duel.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
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
            if(selectedPlayers.size != 0) {
                if (selectedGame.name == R.string.duel && selectedPlayers.size != 2) {
                    showToast("В игре Дуэль должно быть 2 игрока")
                } else {
                    when (selectedGame.name) {
                        R.string.stopwatch -> navController.navigate(R.id.stopWatchGameFragment)
                        R.string.duel -> navController.navigate(R.id.duelGameFragment)
                        R.string.timer -> navController.navigate(R.id.timerGameFragment)
                    }
                }
            } else { showToast("Вы не выбрали игроков") }
        }
    }

}