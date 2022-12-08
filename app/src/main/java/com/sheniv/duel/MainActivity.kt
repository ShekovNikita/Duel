package com.sheniv.duel

import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.sheniv.duel.database.room.PlayerDatabase
import com.sheniv.duel.extantion.*
import com.sheniv.duel.firebase.initFirebase
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
        initFirebase()

    }

    private fun checkUpdateInPlayMarket() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)

        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            // If there is an update available, prepare to promote it.
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // Or 'AppUpdateType.IMMEDIATE for immediate updates.
                    AppUpdateType.FLEXIBLE,
                    // The current activity.
                    this,
                    1
                )
            }

            // If the process of downloading is finished, start the completion flow.
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                Snackbar.make(
                    View(this),
                    "An update has just been downloaded from Google Play",
                    Snackbar.LENGTH_INDEFINITE
                ).apply {
                    setAction("RELOAD") { appUpdateManager.completeUpdate() }
                    show()
                }
            }
        }
            .addOnFailureListener { e ->
                // Handle the error.
            }
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

        var badge = navView.getOrCreateBadge(R.id.navigation_championship)
        //badge.number = ""
    }
}