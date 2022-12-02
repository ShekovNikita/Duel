package com.sheniv.duel.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sheniv.duel.R
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.databinding.ItemPlayerRatingBinding
import com.sheniv.duel.databinding.ItemRaitingOnlineBinding
import com.sheniv.duel.extantion.beGone
import com.sheniv.duel.extantion.beVisible
import com.sheniv.duel.models.UserOnline

class OnlineRatingAdapter(
    val gameName: Int,
    val players: List<UserOnline>,
    val context: Context
) : RecyclerView.Adapter<OnlineRatingAdapter.PlayerRatingViewHolder>() {

    var listPlayer: ArrayList<UserOnline> = players as ArrayList<UserOnline>
    var sortedList = listOf<UserOnline>()

    init {
        if (gameName == R.string.stopwatch) {
            sortedList = listPlayer.sortedByDescending { it.stopwatch}
        }
        if (gameName == R.string.timer) {
            sortedList = listPlayer.sortedWith(compareBy { it.timer })
        }
    }

    inner class PlayerRatingViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemRaitingOnlineBinding.bind(item)
        val rating = binding.rating
        fun bind(player: UserOnline) = with(binding) {
            best.beVisible()
            textBest.beVisible()
            playerName.text = player.name
            Glide.with(context).load(player.photoUrl).into(photo)

            when (gameName) {
                R.string.stopwatch -> {
                    allGames.text = player.stopwatch_games.toString()
                    best.text = player.stopwatch.toString()
                }
                R.string.timer -> {
                    allGames.text = player.timer_games.toString()
                    best.text = player.timer.toString()
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