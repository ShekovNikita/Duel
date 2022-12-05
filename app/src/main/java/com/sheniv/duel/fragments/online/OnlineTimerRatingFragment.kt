package com.sheniv.duel.fragments.online

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sheniv.duel.R
import com.sheniv.duel.adapters.OnlineRatingAdapter
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentOnlineTimerRatingBinding
import com.sheniv.duel.viewmodels.ChampionshipFragmentViewModel

class OnlineTimerRatingFragment : BaseFragment<FragmentOnlineTimerRatingBinding>() {

    private val viewModel = ChampionshipFragmentViewModel()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOnlineTimerRatingBinding.inflate(inflater, container, false)

    override fun FragmentOnlineTimerRatingBinding.onBindView(savedInstanceState: Bundle?) {

        btnPlay.setOnClickListener{
            navController.navigate(R.id.onlineTimerFragment)
        }

        viewModel.playersLiveData.observe(viewLifecycleOwner){
            recyclerPlayersRating.adapter = OnlineRatingAdapter(R.string.timer, it, requireActivity())
        }
    }

}