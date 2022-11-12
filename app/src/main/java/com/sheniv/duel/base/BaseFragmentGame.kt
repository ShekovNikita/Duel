package com.sheniv.duel.base

import androidx.viewbinding.ViewBinding
import com.sheniv.duel.extantion.selectedPlayers

abstract class BaseFragmentGame<VIEW_BINDING : ViewBinding> : BaseFragment<VIEW_BINDING>() {

    override fun onDestroyView() {
        super.onDestroyView()
        selectedPlayers = arrayListOf()
    }
}