package com.sheniv.duel.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sheniv.duel.R
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.databinding.ItemSelectPlayerBinding
import com.sheniv.duel.extantion.selectedPlayers

class SelectPlayersAdapter(
    private val players: List<Player>
) : RecyclerView.Adapter<SelectPlayersAdapter.SelectPlayerViewHolder>() {

    inner class SelectPlayerViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemSelectPlayerBinding.bind(item)
        fun bind(player: Player) = with(binding) {
            playerName.text = player.name
            checkboxSelectPlayer.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) selectedPlayers.add(player)
                else selectedPlayers.remove(player)
                Log.e("players", "$selectedPlayers")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SelectPlayerViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_select_player, parent, false)
        )

    override fun onBindViewHolder(holder: SelectPlayerViewHolder, position: Int) {
        holder.bind(players[position])
    }

    override fun getItemCount() = players.size
}