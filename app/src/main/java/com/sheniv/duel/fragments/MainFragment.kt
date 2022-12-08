package com.sheniv.duel.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.sheniv.duel.R
import com.sheniv.duel.adapters.interfaces.ClickOnTheGame
import com.sheniv.duel.adapters.GameAdapter
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentMainBinding
import com.sheniv.duel.extantion.*
import com.sheniv.duel.models.Game

class MainFragment : BaseFragment<FragmentMainBinding>(), ClickOnTheGame {

    lateinit var appUpdateManager: AppUpdateManager
    val RC_APP_UPDATE = 100

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    override fun FragmentMainBinding.onBindView(savedInstanceState: Bundle?) {

        appUpdateManager = AppUpdateManagerFactory.create(ACTIVITY)
        checkUpdate()
        bottomNavigationView.beVisible()
        recyclerGames.layoutManager = GridLayoutManager(context, 2)
        recyclerGames.adapter = GameAdapter(this@MainFragment, allGames)

        btnInfo.setOnClickListener {
            showRulesDialog(getString(R.string.thanks), getString(R.string.names_to_thanks))
        }
    }

    private fun checkUpdate() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { result ->
            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){
                appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.FLEXIBLE, ACTIVITY, RC_APP_UPDATE)
            }
        }
        appUpdateManager.registerListener(installStateUpdatedListener)
    }

    private val installStateUpdatedListener =
        InstallStateUpdatedListener { p0 -> if (p0.installStatus() == InstallStatus.DOWNLOADED) showShackBar() }

    override fun onStop() {
        appUpdateManager.registerListener(installStateUpdatedListener)
        super.onStop()
    }

    private fun showShackBar() {
        Snackbar.make(binding.root, "New App is ready", Snackbar.LENGTH_INDEFINITE)
            .setAction("Instal") { appUpdateManager.completeUpdate() }
                .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_APP_UPDATE && resultCode != RESULT_OK){
            showToast(getString(R.string.cancel))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun selectTheGame(game: Game) {
        selectedGame = game
        if (game.name != R.string.soon) navController.navigate(R.id.selectPlayersFragment)
        else gameInfo(game)
    }

    override fun gameInfo(game: Game) {

        var title = ""
        var message = ""

        when (game.name){
            R.string.duel -> {
                title = getString(R.string.duel)
                message = getString(R.string.rules_duel)
                showRulesDialog(title, message)
            }
            R.string.timer -> {
                title = getString(R.string.timer)
                message = getString(R.string.rules_timer)
                showRulesDialog(title, message)
            }
            R.string.stopwatch -> {
                title = getString(R.string.stopwatch)
                message = getString(R.string.rules_stopwatch)
                showRulesDialog(title, message)
            }
            R.string.soon -> showSoonDialog()
        }


    }

    private fun showRulesDialog(title: String, message: String){
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun showSoonDialog(){

        val play = "https://play.google.com/store/apps/details?id=com.sheniv.duel"
        val play_market = Intent(Intent(Intent.ACTION_VIEW, Uri.parse(play)))

        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(getString(R.string.soon))
            .setMessage(getString(R.string.rules_soon))
            .setPositiveButton("OK") { dialog, _ ->
                startActivity(play_market)
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
}