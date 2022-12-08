package com.sheniv.duel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.ui.AppBarConfiguration
import com.sheniv.duel.R
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.extantion.db

class MainActivityViewModel : ViewModel() {

    fun getAppBarConfiguration(): AppBarConfiguration {
        return AppBarConfiguration(
            setOf(
                R.id.navigation_games,
                //R.id.navigation_rating,
            )
        )
    }
}