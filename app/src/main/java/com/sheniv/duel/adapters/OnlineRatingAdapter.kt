package com.sheniv.duel.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sheniv.duel.R
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.databinding.ItemPlayerRatingBinding
import com.sheniv.duel.databinding.ItemRaitingOnlineBinding
import com.sheniv.duel.extantion.beGone
import com.sheniv.duel.extantion.beVisible

class OnlineRatingAdapter(
    val gameName: Int,
    val players: List<Player>
) : RecyclerView.Adapter<OnlineRatingAdapter.PlayerRatingViewHolder>() {

    var listPlayer: ArrayList<Player> = players as ArrayList<Player>
    var sortedList = listOf<Player>()

    init {
        if (gameName == R.string.stopwatch) {
            sortedList = listPlayer.sortedByDescending { it.stopwatchBest }
        }
        if (gameName == R.string.timer) {
            sortedList = listPlayer.sortedWith(compareBy { it.timerBest })
        }
    }

    inner class PlayerRatingViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemRaitingOnlineBinding.bind(item)
        val rating = binding.rating
        fun bind(player: Player) = with(binding) {
            best.beVisible()
            textBest.beVisible()
            playerName.text = player.name

            when (gameName) {
                R.string.stopwatch -> {
                    allGames.text = player.stopwatchGames.toString()
                    best.text = player.stopwatchBest.toString()
                }
                R.string.timer -> {
                    allGames.text = player.timerGames.toString()
                    best.text = player.timerBest.toString()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlayerRatingViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_raiting_online, parent, false)
        )

    override fun onBindViewHolder(holder: PlayerRatingViewHolder, position: Int) {
        holder.bind(sortedList[position])
        holder.rating.text = (position + 1).toString()
    }

    override fun getItemCount() = listPlayer.size
}