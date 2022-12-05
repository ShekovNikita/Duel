package com.sheniv.duel.viewmodels

import androidx.lifecycle.ViewModel
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.extantion.db

class RatingViewModel: ViewModel() {

    private var allPlayers = db.getAll() as ArrayList<Player>

    fun getRatingStopWatch() = allPlayers.sortedByDescending { it.stopwatchBest }

    fun getRatingTimer() = allPlayers.sortedWith(compareBy { it.timerBest })

    fun getRatingDuel() = allPlayers.sortedByDescending { it.duelWins }
}