package com.sheniv.duel.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sheniv.duel.R
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.databinding.ItemPlayerRatingBinding
import com.sheniv.duel.extantion.beGone
import com.sheniv.duel.extantion.beVisible

class PlayerRatingAdapter(
    val gameName: Int,
    val players: List<Player>
) : RecyclerView.Adapter<PlayerRatingAdapter.PlayerRatingViewHolder>() {

    inner class PlayerRatingViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemPlayerRatingBinding.bind(item)
        val rating = binding.rating
        fun bind(player: Player) = with(binding) {
            best.beVisible()
            textBest.beVisible()
            playerName.text = player.name

            when (gameName) {
                R.string.stopwatch -> {
                    allGames.text = player.stopwatchGames.toString()
                    wins.text = player.stopwatchWins.toString()
                    best.text = player.stopwatchBest.toString()
                }
                R.string.timer -> {
                    allGames.text = player.timerGames.toString()
                    wins.text = player.timerWins.toString()
                    best.text = player.timerBest.toString()
                }
                R.string.duel -> {
                    allGames.text = player.duelGames.toString()
                    wins.text = player.duelWins.toString()
                    best.beGone()
                    textBest.beGone()
                }
                R.string.soon -> {
                    allGames.text = "-"
                    wins.text = "-"
                    best.text = "-"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlayerRatingViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_player_rating, parent, false)
        )

    override fun onBindViewHolder(holder: PlayerRatingViewHolder, position: Int) {
        holder.bind(players[position])
        holder.rating.text = (position + 1).toString()
    }

    override fun getItemCount() = players.size
}