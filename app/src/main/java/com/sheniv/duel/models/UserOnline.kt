package com.sheniv.duel.models

data class UserOnline(
    var name: String = "",
    var photoUrl: String = "",
    var stopwatch: Int = 0,
    var stopwatch_games: Int = 0,
    var timer: Int = 5000,
    var timer_games: Int = 0
)