package com.sheniv.duel.fragments.rating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sheniv.duel.R
import com.sheniv.duel.adapters.PlayerRatingAdapter
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentRatingBinding
import com.sheniv.duel.databinding.FragmentTimerRatingBinding
import com.sheniv.duel.viewmodels.RatingViewModel

class TimerRatingFragment : BaseFragment<FragmentTimerRatingBinding>() {

    private val viewModel = RatingViewModel()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTimerRatingBinding.inflate(inflater, container, false)

    override fun FragmentTimerRatingBinding.onBindView(savedInstanceState: Bundle?) {

        recyclerPlayersRating.adapter = PlayerRatingAdapter(R.string.timer, viewModel.getRatingTimer())

        btnUpdate.setOnClickListener {
            recyclerPlayersRating.adapter = PlayerRatingAdapter(R.string.timer, viewModel.getRatingTimer())
        }
    }

}