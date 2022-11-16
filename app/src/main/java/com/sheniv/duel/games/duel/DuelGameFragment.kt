package com.sheniv.duel.games.duel

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.LinearLayout
import com.sheniv.duel.R
import com.sheniv.duel.base.BaseFragmentGame
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.databinding.FragmentDuelGameBinding
import com.sheniv.duel.extantion.*

class DuelGameFragment : BaseFragmentGame<FragmentDuelGameBinding>() {

    var timer: CountDownTimer? = null

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDuelGameBinding.inflate(inflater, container, false)

    override fun FragmentDuelGameBinding.onBindView(savedInstanceState: Bundle?) {

        firstPlayer.text = selectedPlayers[0].name
        secondPlayer.text = selectedPlayers[1].name

        timer = object : CountDownTimer(4000, 1000){
            override fun onTick(p0: Long) {
                timerText.beVisible()
                timerText.text = (p0/1000).toString()
                if (p0 < 1000L){
                    timerText.text = "GO"
                }
            }

            override fun onFinish() {
                playGame()
            }
        }.start()

    }

    private fun playGame() {
        binding.timerText.beGone()

        val firstPlayer = db.getById(selectedPlayers[0].id!!)
        val secondPlayer = db.getById(selectedPlayers[1].id!!)

        binding.firstPlayer.setOnClickListener {
            binding.firstPlayer.height += 50
            binding.secondPlayer.height -= 50
            if (binding.firstPlayer.height > (binding.secondPlayer.height + 350)){
                firstPlayer.id?.let { id1 ->
                    db.updateDuel(firstPlayer.duelGames + 1, firstPlayer.duelWins + 1,
                        id1
                    )
                }
                secondPlayer.id?.let { id2 ->
                    db.updateDuel(secondPlayer.duelGames + 1, secondPlayer.duelWins,
                        id2
                    )
                }
                bestPlayer = firstPlayer
                navController.navigate(R.id.winnerCardFragment)
            }
        }
        binding.secondPlayer.setOnClickListener {
            binding.firstPlayer.height -= 50
            binding.secondPlayer.height += 50
            if ((binding.firstPlayer.height + 350) < binding.secondPlayer.height){
                firstPlayer.id?.let { id1 ->
                    db.updateDuel(firstPlayer.duelGames + 1, firstPlayer.duelWins,
                        id1
                    )
                }
                secondPlayer.id?.let { id2 ->
                    db.updateDuel(secondPlayer.duelGames + 1, secondPlayer.duelWins + 1,
                        id2
                    )
                }
                bestPlayer = secondPlayer
                navController.navigate(R.id.winnerCardFragment)
            }
        }
    }
}