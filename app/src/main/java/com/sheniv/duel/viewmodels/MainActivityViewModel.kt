package com.sheniv.duel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.ui.AppBarConfiguration
import com.sheniv.duel.R

class MainActivityViewModel : ViewModel() {

    fun getAppBarConfiguration(): AppBarConfiguration {
        return AppBarConfiguration(
            setOf(
                R.id.navigation_games,
                R.id.navigation_rating,
                R.id.navigation_players
            )
        )
    }
}