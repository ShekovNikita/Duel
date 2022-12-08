package com.sheniv.duel.fragments.rating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sheniv.duel.R
import com.sheniv.duel.adapters.PlayerRatingAdapter
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentDuelRatingBinding
import com.sheniv.duel.viewmodels.RatingViewModel

class DuelRatingFragment : BaseFragment<FragmentDuelRatingBinding>() {

    private val viewModel = RatingViewModel()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDuelRatingBinding.inflate(inflater, container, false)

    override fun FragmentDuelRatingBinding.onBindView(savedInstanceState: Bundle?) {
        recyclerPlayersRating.adapter = PlayerRatingAdapter(R.string.duel, viewModel.getRatingDuel())

        btnUpdate.setOnClickListener {
            recyclerPlayersRating.adapter = PlayerRatingAdapter(R.string.duel, viewModel.getRatingDuel())
        }
    }

}