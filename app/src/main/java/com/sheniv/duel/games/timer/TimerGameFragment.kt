package com.sheniv.duel.games.timer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sheniv.duel.R
import com.sheniv.duel.base.BaseFragmentGame
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.databinding.FragmentTimerGameBinding
import com.sheniv.duel.extantion.beGone
import com.sheniv.duel.extantion.beVisible
import com.sheniv.duel.extantion.db
import com.sheniv.duel.extantion.selectedPlayers

class TimerGameFragment : BaseFragmentGame<FragmentTimerGameBinding>() {

    var timer: CountDownTimer? = null
    var player = 0
    var counter = 0
    val allPlayerInThisGame = arrayListOf<Player>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTimerGameBinding.inflate(inflater, container, false)

    override fun FragmentTimerGameBinding.onBindView(savedInstanceState: Bundle?) {

        timer = object : CountDownTimer(5000, 1) {
            override fun onTick(seconds: Long) {
                timerText.text = String.format("%1d:%03d", seconds / 1000, seconds % 1000)
                playerName.text = "Стоп"
                playerName.isClickable = true
                playerName.setOnClickListener {
                    this.cancel()
                    counter = seconds.toInt()
                    timerText.text = String.format("%1d:%03d", counter / 1000, counter % 1000)

                    val currentPlayer = selectedPlayers[player]

                    playerName.text = currentPlayer.name
                    if (counter < currentPlayer.timerBest) {
                        currentPlayer.id?.let { it ->
                            db.updateTimer(currentPlayer.timerGames + 1, counter, it)
                        }
                    } else currentPlayer.id?.let { it1 ->
                        db.updateTimer(
                            currentPlayer.timerGames + 1, currentPlayer.timerBest,
                            it1
                        )
                    }
                    allPlayerInThisGame.add(
                        Player(
                            name = currentPlayer.name,
                            timerBest = counter,
                            id = currentPlayer.id,
                            timerWins = currentPlayer.timerWins
                        )
                    )

                    btnStart.beVisible()
                    btnStart.text = "Далее"
                    btnStart.setOnClickListener {
                        player++
                        onResume()
                    }
                }
            }

            override fun onFinish() {
                val currentPlayer = selectedPlayers[player]
                timerText.text = "Время вышло"
                playerName.isClickable = false
                currentPlayer.id?.let {
                    db.updateTimer(
                        currentPlayer.timerGames + 1, currentPlayer.timerBest,
                        it
                    )
                }
                allPlayerInThisGame.add(Player(name = currentPlayer.name, timerBest = 5000))
                player++
                btnStart.beVisible()
                btnStart.text = "Далее"
                btnStart.setOnClickListener { onResume() }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        var winCard = binding.winCard
        winCard.root.beGone()

        if (player < selectedPlayers.size) {
            binding.timerText.beVisible()
            binding.playerName.isClickable = false
            binding.playerName.setText(R.string.timer)
            counter = 0
            binding.timerText.text = "5:000"
            binding.btnStart.beVisible()
            binding.btnStart.text = selectedPlayers[player].name
            binding.btnStart.setOnClickListener {
                binding.playerName.isClickable = true
                timer?.start()
                binding.btnStart.beGone()
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
                    if (i.timerBest < bestPlayer.timerBest) bestPlayer = i
                }
                bestPlayer.id?.let { db.updateTimerWin(bestPlayer.timerWins + 1, it) }
                winCard.winnerName.text = "${bestPlayer.name}"
                winCard.result.text = "${bestPlayer.timerBest} мс"
            }



            winCard.btnPlayers.setOnClickListener {
                navController.popBackStack()
            }
            winCard.btnGames.setOnClickListener {
                navController.popBackStack()
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
