package com.sheniv.duel.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "players")
data class Player (

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    //@ColumnInfo(name = "name")
    val name: String,

    //@ColumnInfo(name = "duel")
    var duel: Int = 0,
    @ColumnInfo(defaultValue = "0")
    var duelGames: Int = 0,
    @ColumnInfo(defaultValue = "0")
    val duelWins: Int = 0,

    //@ColumnInfo(name = "timer")
    var timerWins: Int = 0,
    @ColumnInfo(defaultValue = "0")
    val timerGames: Int = 0,
    @ColumnInfo(defaultValue = "0")
    val timerBest: Int = 5000,

    //@ColumnInfo(name = "stopwatch")
    var stopwatchWins: Int = 0,
    @ColumnInfo(defaultValue = "0")
    var stopwatchGames: Int = 0,
    @ColumnInfo(defaultValue = "0")
    var stopwatchBest: Int = 0,
) {

}
