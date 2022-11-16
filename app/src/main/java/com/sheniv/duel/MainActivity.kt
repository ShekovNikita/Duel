package com.sheniv.duel

import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sheniv.duel.database.room.PlayerDatabase
import com.sheniv.duel.extantion.*
import com.sheniv.duel.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val viewModelMain = MainActivityViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDatabaseRoom(this)
        ACTIVITY = this
        supportActionBar?.hide()
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        selectedPlayers.clear()
        Log.e("selectedPlayers", "$selectedPlayers")
    }

    private fun bottomNavigation() {
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController, viewModelMain.getAppBarConfiguration())
        NavigationUI.setupWithNavController(navView, navController)
    }
}