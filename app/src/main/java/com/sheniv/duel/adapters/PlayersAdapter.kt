package com.sheniv.duel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sheniv.duel.R
import com.sheniv.duel.adapters.PlayersAdapter.PlayerViewHolder
import com.sheniv.duel.adapters.interfaces.DeletePlayer
import com.sheniv.duel.adapters.interfaces.PlayerInfo
import com.sheniv.duel.adapters.interfaces.UpdatePlayer
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.databinding.ItemPlayerBinding
import com.sheniv.duel.extantion.beGone
import com.sheniv.duel.extantion.beVisible

class PlayersAdapter(
    private val deletePlayer: DeletePlayer,
    private val updatePlayer: UpdatePlayer,
    private val playerInfo: PlayerInfo
) : RecyclerView.Adapter<PlayerViewHolder>() {

    inner class PlayerViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemPlayerBinding.bind(item)
        fun bind(player: Player) = with(binding) {
            playerName.text = player.name
            btnDelete.beGone()
            btnUpdate.beGone()
            checkboxSetting.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    btnDelete.beVisible()
                    btnUpdate.beVisible()
                } else {
                    btnDelete.beGone()
                    btnUpdate.beGone()
                }
            }
            btnDelete.setOnClickListener {
                deletePlayer.deletePlayer(player)
            }
            btnUpdate.setOnClickListener {
                updatePlayer.updatePlayer(player)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlayerViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_player, parent, false)
        )

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.itemView.setOnClickListener { playerInfo.playerInfo(differ.currentList[position]) }
        holder.setIsRecyclable(true)
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<Player>(){
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}