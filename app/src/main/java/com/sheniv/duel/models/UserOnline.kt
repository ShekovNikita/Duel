package com.sheniv.duel.models

data class UserOnline(
    var name: String = "",
    var photoUrl: String = "",
    var stopwatch_last: Int = 0,
    var stopwatch_best: Int = 0,
    var stopwatch_games: Int = 0,
    var timer_last: Int = 5000,
    var timer_best: Int = 5000,
    var timer_games: Int = 0
)