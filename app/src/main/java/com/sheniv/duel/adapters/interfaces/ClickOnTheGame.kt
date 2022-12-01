package com.sheniv.duel.adapters.interfaces

import com.sheniv.duel.models.Game

interface ClickOnTheGame {

    fun selectTheGame(game: Game)

    fun gameInfo(game: Game)
}