package com.sheniv.duel.database.room

import androidx.room.*

@Dao
interface PlayerDatabaseDao {

    @Query("SELECT * from players")
    fun getAll(): List<Player>

    @Query("SELECT * from players where id = :id")
    fun getById(id: Int) : Player

    @Query("SELECT * FROM players WHERE id IN (:playersId)")
    fun getCurrentPlayers(playersId: List<Int>): List<Player>

    @Insert
    fun insert(player : Player)

    @Update
    fun update(player : Player)

    @Query("UPDATE players SET name=:newName WHERE id = :id")
    fun updateName(newName: String, id: Int)

    @Query("UPDATE players SET timerGames=:timerGames, timerBest=:timerBest WHERE id = :id")
    fun updateTimer(timerGames: Int, timerBest: Int, id: Int)

    @Query("UPDATE players SET timerWins=:timerWins WHERE id = :id")
    fun updateTimerWin(timerWins: Int, id: Int)

    @Query("UPDATE players SET stopwatchGames=:stopwatchGames, stopwatchBest=:stopwatchBest WHERE id = :id")
    fun updateStopwatch(stopwatchGames: Int, stopwatchBest: Int, id: Int)

    @Query("UPDATE players SET stopwatchWins=:stopwatchWins WHERE id = :id")
    fun updateStopwatchWin(stopwatchWins: Int, id: Int)

    @Query("UPDATE players SET duelGames=:duelGames, duelWins =:duelWins WHERE id = :id")
    fun updateDuel(duelGames: Int, duelWins: Int, id: Int)

    @Delete
    fun delete(player : Player)

    @Query("DELETE FROM players")
    fun deleteAllPlayers()
}