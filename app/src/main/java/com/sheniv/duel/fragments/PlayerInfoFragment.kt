package com.sheniv.duel.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentPlayerInfoBinding
import com.sheniv.duel.extantion.currentPlayerInfo

class PlayerInfoFragment : BaseFragment<FragmentPlayerInfoBinding>() {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlayerInfoBinding.inflate(inflater, container, false)

    override fun FragmentPlayerInfoBinding.onBindView(savedInstanceState: Bundle?) {
        playerName.text = currentPlayerInfo.name

        //stopwatch
        stopwatchAllGame.text = currentPlayerInfo.stopwatchGames.toString()
        stopwatchWins.text = currentPlayerInfo.stopwatchWins.toString()
        stopwatchBest.text = currentPlayerInfo.stopwatchBest.toString()

        //duel
        duelAllGame.text = currentPlayerInfo.duelGames.toString()
        duelWins.text = currentPlayerInfo.duelWins.toString()
        duelBest.text = currentPlayerInfo.duel.toString()

        //timer
        timerAllGame.text = currentPlayerInfo.timerGames.toString()
        timerWins.text = currentPlayerInfo.timerWins.toString()
        timerBest.text = currentPlayerInfo.timerBest.toString()
    }

}