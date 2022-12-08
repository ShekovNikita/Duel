package com.sheniv.duel.viewmodels

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.extantion.db
import com.sheniv.duel.models.UserOnline
import kotlinx.coroutines.launch
import java.io.Closeable

class RatingViewModel: ViewModel() {

    fun getRatingStopWatch() = db.getAll().sortedByDescending { it.stopwatchBest }

    fun getRatingTimer() = db.getAll().sortedWith(compareBy { it.timerBest })

    fun getRatingDuel() = db.getAll().sortedByDescending { it.duelWins }
}