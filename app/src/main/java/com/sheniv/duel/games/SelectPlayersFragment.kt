package com.sheniv.duel.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.app.SharedElementCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.sheniv.duel.R
import com.sheniv.duel.adapters.SelectPlayersAdapter
import com.sheniv.duel.adapters.interfaces.DeletePlayer
import com.sheniv.duel.adapters.interfaces.UpdatePlayer
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.databinding.FragmentSelectPlayersBinding
import com.sheniv.duel.extantion.*

class SelectPlayersFragment : BaseFragment<FragmentSelectPlayersBinding>(), DeletePlayer,
    UpdatePlayer {

    lateinit var dialogView: View
    lateinit var name: EditText
    lateinit var inputLayout: TextInputLayout
    lateinit var builder: MaterialAlertDialogBuilder

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSelectPlayersBinding.inflate(inflater, container, false)

    override fun FragmentSelectPlayersBinding.onBindView(savedInstanceState: Bundle?) {
        bottomNavigationView.beGone()

        updatePlayers()

        btnContinue.setOnClickListener {
            checkPlayersSize()
        }

        btnAddPlayer.setOnClickListener {
            newPlayerDialog()
        }
    }

    private fun checkPlayersSize() {
        if(selectedPlayers.size > 1) {
            if (selectedGame.name == R.string.duel && selectedPlayers.size != 2) {
                showToast(getString(R.string.duel_must_have_2_players))
            } else {
                when (selectedGame.name) {
                    R.string.stopwatch -> navController.navigate(R.id.stopWatchGameFragment)
                    R.string.duel -> navController.navigate(R.id.duelGameFragment)
                    R.string.timer -> navController.navigate(R.id.timerGameFragment)
                }
            }
        } else {
            showToast(getString(R.string.minimum_2_players_can_play))
        }
    }

    private fun updatePlayers(){
        binding.recyclerPlayers.adapter = SelectPlayersAdapter(db.getAll(), this, this)
    }

    private fun initFieldsDialog() {
        dialogView =
            LayoutInflater.from(requireActivity()).inflate(R.layout.add_player_dialog, null)
        name = dialogView.findViewById<EditText>(R.id.input_text)
        inputLayout = dialogView.findViewById(R.id.input_layout)
        builder = MaterialAlertDialogBuilder(requireActivity()).setView(dialogView)
    }

    private fun deletePlayerDialog(player: Player) {
        initFieldsDialog()

        inputLayout.beGone()
        builder.setTitle(getString(R.string.delete) + " ${player.name}?")
            .setPositiveButton(getString(R.string.delete)) { dialog, _ ->
                db.delete(player)
                updatePlayers()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun updateNameDialog(player: Player) {
        initFieldsDialog()

        inputLayout.hint = getString(R.string.change_name)
        name.setText(player.name)
        builder.setPositiveButton(getString(R.string.change)) { dialog, _ ->
            val playerName = name.text.toString().trim()
            if (playerName.isNotEmpty()) {
                db.updateName(newName = playerName, id = player.id!!)
                updatePlayers()
            } else showToast(getString(R.string.the_name_can_not_be_empty))
            dialog.dismiss()
        }
            .setNegativeButton(getString(R.string.cancel), null)
        builder.show()
    }

    private fun newPlayerDialog() {
        initFieldsDialog()

        inputLayout.hint = getString(R.string.enter_name)
        builder.setTitle(getString(R.string.add_new_player))
            .setPositiveButton(getString(R.string.save)) { dialog, _ ->
                val playerName = name.text.toString().trim()
                if (playerName.isNotEmpty()) {
                    db.insert(Player(name = playerName))
                    updatePlayers()
                } else showToast(getString(R.string.the_name_can_not_be_empty))
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel), null)
        builder.show()
    }

    override fun deletePlayer(player: Player) {
        deletePlayerDialog(player)
    }

    override fun updatePlayer(player: Player) {
        updateNameDialog(player)
    }
}