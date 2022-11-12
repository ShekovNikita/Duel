package com.sheniv.duel.games.stopwatch

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sheniv.duel.R
import com.sheniv.duel.base.BaseFragmentGame
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.databinding.FragmentStopWatchGameBinding
import com.sheniv.duel.extantion.beGone
import com.sheniv.duel.extantion.beVisible
import com.sheniv.duel.extantion.db
import com.sheniv.duel.extantion.selectedPlayers

class StopWatchGameFragment : BaseFragmentGame<FragmentStopWatchGameBinding>() {

    var timer: CountDownTimer? = null
    var player = 0
    var counter = 0
    val allPlayerInThisGame = arrayListOf<Player>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentStopWatchGameBinding.inflate(inflater, container, false)

    override fun FragmentStopWatchGameBinding.onBindView(savedInstanceState: Bundle?) {

        timer = object : CountDownTimer(5000, 1) {
            override fun onTick(seconds: Long) {
                timerText.text = String.format("%1d:%03d", seconds / 1000, seconds % 1000)
            }

            override fun onFinish() {
                playerCounter.isClickable = false
                timerText.text = selectedPlayers[player].name
                val currentPlayer = selectedPlayers[player]

                if (currentPlayer.stopwatchBest < counter) {
                    currentPlayer.id?.let {
                        db.updateStopwatch(
                            currentPlayer.stopwatchGames + 1, counter, it
                        )
                    }
                } else {
                    currentPlayer.id?.let {
                        db.updateStopwatch(
                            currentPlayer.stopwatchGames + 1, currentPlayer.stopwatchBest, it
                        )
                    }
                }
                allPlayerInThisGame.add(
                    Player(
                        name = currentPlayer.name,
                        stopwatchBest = counter,
                        stopwatchWins = currentPlayer.stopwatchWins,
                        id = currentPlayer.id
                    )
                )

                btnStart.beVisible()
                btnStart.text = "Далее"
            }
        }
    }

    override fun onResume() {
        super.onResume()

        var winCard = binding.winCard
        winCard.root.beGone()

        if (player < selectedPlayers.size) {
            counter = 0
            with(binding) {
                playerCounter.textSize = 100f
                timerText.beVisible()
                timerText.text = "5:000"
                btnStart.beVisible()
                btnStart.text = selectedPlayers[player].name
                playerCounter.text = counter.toString()
            }
            binding.btnStart.setOnClickListener {
                binding.playerCounter.isClickable = true
                timer?.start()
                binding.btnStart.beGone()
                binding.playerCounter.setOnClickListener {
                    counter++
                    binding.playerCounter.text = counter.toString()

                    binding.btnStart.setOnClickListener {
                        player++
                        binding.timerText.text = "5:000"
                        onResume()
                    }
                }
            }
        } else {
            var bestPlayer: Player
            binding.timerText.beGone()
            winCard.root.beVisible()
            binding.btnStart.beGone()

            //Glide.with(requireActivity()).load(R.drawable.salut_2).into(winCard.imageSalut)

            if (allPlayerInThisGame.size != 1) {
                bestPlayer = allPlayerInThisGame[0]
                for (i in allPlayerInThisGame) {
                    if (i.stopwatchBest > bestPlayer.stopwatchBest) bestPlayer = i
                }
                bestPlayer.id?.let { db.updateStopwatchWin(bestPlayer.stopwatchWins + 1, it) }
                winCard.winnerName.text = "${bestPlayer.name}"
                winCard.result.text = "${bestPlayer.stopwatchBest}"
            }
            winCard.btnPlayers.setOnClickListener {
                navController.popBackStack()
            }
            winCard.btnGames.setOnClickListener {
                navController.navigate(R.id.navigation_games)
            }
            winCard.btnRepeat.setOnClickListener {
                player = 0
                allPlayerInThisGame.clear()
                onResume()
            }
        }
    }

}