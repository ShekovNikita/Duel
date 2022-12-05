package com.sheniv.duel.fragments.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sheniv.duel.R
import com.sheniv.duel.adapters.FragmentStateAdapter
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentRatingBinding
import com.sheniv.duel.models.AllGames

class RatingFragment : BaseFragment<FragmentRatingBinding>() {

    private val topFragment = listOf<Fragment>(
        StopWatchRatingFragment(),
        TimerRatingFragment(),
        DuelRatingFragment()
    )

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRatingBinding.inflate(inflater, container, false)

    override fun FragmentRatingBinding.onBindView(savedInstanceState: Bundle?) {

        val tabLayout = requireView().findViewById<TabLayout>(R.id.tab_layout)

        viewPager2.adapter = FragmentStateAdapter(requireActivity(), topFragment)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            val games = AllGames().allGameWithRating()
            tab.setIcon(games[position].icon)
            tab.setText(games[position].name)
        }.attach()
    }
}