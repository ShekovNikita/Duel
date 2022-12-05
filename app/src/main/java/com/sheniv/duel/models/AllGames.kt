package com.sheniv.duel.models

import com.sheniv.duel.R

class AllGames {

    private val stopWatch = Game(R.string.stopwatch, R.drawable.ic_stopwatch_100)
    private val duel = Game(R.string.duel, R.drawable.ic_duel_100)
    private val timer = Game(R.string.timer, R.drawable.ic_timer_100)

    private val soon = Game(R.string.soon, R.drawable.ic_question_24)

    fun allGames() : ArrayList<Game>{
        val allGames : ArrayList<Game> = arrayListOf()
        allGames.add(stopWatch)
        allGames.add(duel)
        allGames.add(timer)

        allGames.add(soon)
        return allGames
    }

    fun onlineGames() : ArrayList<Game>{
        val allGames : ArrayList<Game> = arrayListOf()
        allGames.add(stopWatch)
        allGames.add(timer)

        return allGames
    }

    fun getOnlineIcon(): ArrayList<Int>{
        val icons = arrayListOf<Int>()
        onlineGames().forEach { icons.add(it.icon) }
        return icons
    }

    fun getOnlineName(): ArrayList<Int>{
        val names = arrayListOf<Int>()
        onlineGames().forEach { names.add(it.name) }
        return names
    }
}