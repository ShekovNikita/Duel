package com.sheniv.duel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.sheniv.duel.R
import com.sheniv.duel.adapters.ClickOnTheGame
import com.sheniv.duel.adapters.GameAdapter
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.database.room.PlayerDatabase
import com.sheniv.duel.databinding.FragmentMainBinding
import com.sheniv.duel.extantion.*
import com.sheniv.duel.models.Game

class MainFragment : BaseFragment<FragmentMainBinding>(), ClickOnTheGame {

    lateinit var dialogView: View
    lateinit var name: EditText
    lateinit var inputLayout: TextInputLayout
    lateinit var builder: MaterialAlertDialogBuilder

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    override fun FragmentMainBinding.onBindView(savedInstanceState: Bundle?) {

        bottomNavigationView.beVisible()
        recyclerGames.layoutManager = GridLayoutManager(context, 2)
        recyclerGames.adapter = GameAdapter(this@MainFragment, allGames)
    }

    override fun selectTheGame(game: Game) {
        selectedGame = game
        if (game.name != R.string.soon) navController.navigate(R.id.selectPlayersFragment)
    }

    private fun initFieldsDialog() {
        dialogView =
            LayoutInflater.from(requireActivity()).inflate(R.layout.add_player_dialog, null)
        name = dialogView.findViewById<EditText>(R.id.input_text)
        inputLayout = dialogView.findViewById(R.id.input_layout)
        builder = MaterialAlertDialogBuilder(requireActivity()).setView(dialogView)
    }

    override fun gameInfo(game: Game) {
        initFieldsDialog()

        inputLayout.beGone()
        name.beGone()

        var title = ""
        var message = ""

        when (game.name){
            R.string.duel -> {
                title = getString(R.string.duel)
                message = "Правила игры"
            }
            R.string.timer -> {
                title = getString(R.string.timer)
                message = "Правила игры"
            }
            R.string.stopwatch -> {
                title = getString(R.string.stopwatch)
                message = "Правила игры"
            }
        }

        builder.setTitle(title).setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}