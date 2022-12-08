package com.sheniv.duel.games

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.ui.navigateUp
import com.sheniv.duel.R
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.databinding.FragmentWinnerCardBinding
import com.sheniv.duel.extantion.*
import com.sheniv.duel.viewmodels.MainActivityViewModel

class WinnerCardFragment : BaseFragment<FragmentWinnerCardBinding>() {
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentWinnerCardBinding.inflate(inflater, container, false)

    override fun FragmentWinnerCardBinding.onBindView(savedInstanceState: Bundle?) {

        val players = arrayListOf<Player>()

        winnerName.text = bestPlayer.name
        result.beVisible()
        resultText.beVisible()
        when (selectedGame.name) {
            R.string.stopwatch -> result.text = bestPlayer.stopwatchBest.toString()
            R.string.timer -> result.text = bestPlayer.timerBest.toString()
            R.string.duel -> {
                result.beGone()
                resultText.beGone()
            }
        }

        btnPlayers.setOnClickListener {
            selectedPlayers.clear()
            navController.navigate(R.id.selectPlayersFragment)
        }
        btnGames.setOnClickListener {
            selectedPlayers.clear()
            navController.popBackStack()
            navController.navigate(R.id.navigation_games)
        }
        btnRepeat.setOnClickListener {
            bestPlayer = Player()
            for (i in selectedPlayers) {
                i.id?.let { it1 -> db.getById(it1).let { it2 -> players.add(it2) } }
            }
            selectedPlayers = players
            Log.e("selectedWin", "$selectedPlayers")
            when (selectedGame.name) {
                R.string.stopwatch -> navController.navigate(R.id.stopWatchGameFragment)
                R.string.timer -> navController.navigate(R.id.timerGameFragment)
                R.string.duel -> navController.navigate(R.id.duelGameFragment)
            }
        }
    }

}