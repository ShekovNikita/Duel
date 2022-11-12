package com.sheniv.duel.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentHelpBinding

class HelpFragment : BaseFragment<FragmentHelpBinding>() {
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHelpBinding.inflate(inflater, container, false)

    override fun FragmentHelpBinding.onBindView(savedInstanceState: Bundle?) {
    }

}