package com.sheniv.duel.fragments.online

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sheniv.duel.R
import com.sheniv.duel.adapters.OnlineRatingAdapter
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentOnlineStopwatchRatingBinding
import com.sheniv.duel.viewmodels.ChampionshipFragmentViewModel

class OnlineStopwatchRatingFragment : BaseFragment<FragmentOnlineStopwatchRatingBinding>() {

    val viewModel = ChampionshipFragmentViewModel()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOnlineStopwatchRatingBinding.inflate(inflater, container, false)

    override fun FragmentOnlineStopwatchRatingBinding.onBindView(savedInstanceState: Bundle?) {

        btnPlay.setOnClickListener {
            navController.navigate(R.id.onlineStopwatchFragment)
        }

        viewModel.playersLiveData.observe(viewLifecycleOwner){
            recyclerPlayersRating.adapter = OnlineRatingAdapter(R.string.stopwatch, it, requireActivity())
        }
    }

}