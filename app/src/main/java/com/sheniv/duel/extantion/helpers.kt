package com.sheniv.duel.extantion

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.database.room.PlayerDatabase
import com.sheniv.duel.database.room.PlayerDatabaseDao
import com.sheniv.duel.models.AllGames
import com.sheniv.duel.models.Game
import com.sheniv.duel.viewmodels.RatingViewModel

lateinit var bottomNavigationView: BottomNavigationView
lateinit var selectedGame: Game
var selectedPlayers = arrayListOf<Player>()
val allGames = AllGames().allGames()
lateinit var db: PlayerDatabaseDao
lateinit var bestPlayer: Player
const val AD_UNIT_ID = "ca-app-pub-2440252298457934/3752907207"
lateinit var ACTIVITY : Activity
lateinit var viewModelRating : RatingViewModel


fun View.beVisible() {
    this.visibility = View.VISIBLE
}

fun View.beGone() {
    this.visibility = View.GONE
}

fun View.beInvisible() {
    this.visibility = View.INVISIBLE
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun initDatabaseRoom(context: Context) {
    val databaseDao = Room.databaseBuilder(
        context,
        PlayerDatabase::class.java,
        "players"
    ).allowMainThreadQueries().build()
    db = databaseDao.todoDao()
    viewModelRating = RatingViewModel()
}