package com.sheniv.duel.games.duel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sheniv.duel.base.BaseFragmentGame
import com.sheniv.duel.databinding.FragmentDuelGameBinding

class DuelGameFragment : BaseFragmentGame<FragmentDuelGameBinding>() {
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDuelGameBinding.inflate(inflater, container, false)

    override fun FragmentDuelGameBinding.onBindView(savedInstanceState: Bundle?) {

    }

}