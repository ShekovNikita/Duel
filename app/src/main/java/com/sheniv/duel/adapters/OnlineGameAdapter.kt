package com.sheniv.duel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sheniv.duel.R
import com.sheniv.duel.adapters.interfaces.ClickOnTheGame
import com.sheniv.duel.databinding.ItemGameBinding
import com.sheniv.duel.databinding.ItemGameOnlineBinding
import com.sheniv.duel.models.Game

class OnlineGameAdapter (
    private val clickOnTheGame: ClickOnTheGame,
    private val gameList: ArrayList<Game>
) : RecyclerView.Adapter<OnlineGameAdapter.GameViewHolder>() {

    inner class GameViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val binding = ItemGameOnlineBinding.bind(item)
        fun bind(game: Game) = with(binding) {
            iconGame.setImageResource(game.icon)
            nameOfTheGame.setText(game.name)
            btnPlay.setOnClickListener { clickOnTheGame.gameInfo(game) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GameViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_game_online, parent, false)
        )

    override fun onBindViewHolder(holder: OnlineGameAdapter.GameViewHolder, position: Int) {
        holder.bind(gameList[position])
        holder.itemView.setOnClickListener {
            clickOnTheGame.selectTheGame(gameList[position])
        }
    }

    override fun getItemCount() = gameList.size
}