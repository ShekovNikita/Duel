package com.sheniv.duel.adapters

import com.sheniv.duel.models.Game

interface ClickOnTheGame {

    fun selectTheGame(game: Game)

    fun gameInfo(game: Game)
}