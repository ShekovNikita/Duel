package com.sheniv.duel.extantion

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.database.room.PlayerDatabase
import com.sheniv.duel.database.room.PlayerDatabaseDao
import com.sheniv.duel.models.AllGames
import com.sheniv.duel.models.Game

lateinit var bottomNavigationView: BottomNavigationView
lateinit var selectedGame: Game
var selectedPlayers = arrayListOf<Player>()
val allGames = AllGames().allGames()
lateinit var db: PlayerDatabaseDao
lateinit var currentPlayerInfo: Player
lateinit var bestPlayer: Player
const val AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"


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
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun initDatabaseRoom(context: Context) {
    val databaseDao = Room.databaseBuilder(
        context,
        PlayerDatabase::class.java,
        "players"
    ).allowMainThreadQueries().build()
    db = databaseDao.todoDao()
}