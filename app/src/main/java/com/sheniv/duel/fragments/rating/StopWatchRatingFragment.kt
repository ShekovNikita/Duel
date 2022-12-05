package com.sheniv.duel.fragments.rating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sheniv.duel.R
import com.sheniv.duel.adapters.PlayerRatingAdapter
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.databinding.FragmentStopWatchRatingBinding
import com.sheniv.duel.viewmodels.RatingViewModel

class StopWatchRatingFragment : BaseFragment<FragmentStopWatchRatingBinding>() {

    val viewModel = RatingViewModel()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentStopWatchRatingBinding.inflate(inflater, container, false)

    override fun FragmentStopWatchRatingBinding.onBindView(savedInstanceState: Bundle?) {

        recyclerPlayersRating.adapter = PlayerRatingAdapter(R.string.stopwatch, viewModel.getRatingStopWatch())
    }

}