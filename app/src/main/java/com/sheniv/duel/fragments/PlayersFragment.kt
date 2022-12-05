package com.sheniv.duel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.sheniv.duel.R
import com.sheniv.duel.adapters.interfaces.DeletePlayer
import com.sheniv.duel.adapters.PlayersAdapter
import com.sheniv.duel.adapters.interfaces.UpdatePlayer
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.databinding.FragmentPlayersBinding
import com.sheniv.duel.extantion.beGone
import com.sheniv.duel.extantion.db
import com.sheniv.duel.extantion.showToast

class PlayersFragment : BaseFragment<FragmentPlayersBinding>(), DeletePlayer, UpdatePlayer{

    private val simpleAdapter by lazy { PlayersAdapter(this, this) }

    lateinit var dialogView: View
    lateinit var name: EditText
    lateinit var inputLayout: TextInputLayout
    lateinit var builder: MaterialAlertDialogBuilder

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlayersBinding.inflate(inflater, container, false)

    override fun FragmentPlayersBinding.onBindView(savedInstanceState: Bundle?) {

        updateList()

        recyclerPlayers.adapter = simpleAdapter

        btnAddPlayer.setOnClickListener {
            newPlayerDialog()
        }
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
                updateList()
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
            val playerName = name.text.toString()
            if (playerName.isNotEmpty()) {
                db.updateName(newName = playerName, id = player.id!!)
                updateList()
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
                val playerName = name.text.toString()
                if (playerName.isNotEmpty()) {
                    db.insert(Player(name = playerName))
                    updateList()
                } else showToast(getString(R.string.the_name_can_not_be_empty))
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel), null)
        builder.show()
    }

    override fun deletePlayer(player: Player) {
        deletePlayerDialog(player)
    }

    private fun updateList() {
        simpleAdapter.differ.submitList(db.getAll())
    }

    override fun updatePlayer(player: Player) {
        updateNameDialog(player)
    }
}