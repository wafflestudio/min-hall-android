package com.wafflestudio.snucse.minhall.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafflestudio.snucse.minhall.databinding.FragmentSettingBinding
import com.wafflestudio.snucse.minhall.view.AppBar
import com.wafflestudio.snucse.minhall.view.ui.base.BaseFragment

class SettingFragment : BaseFragment() {

    companion object {
        const val TAG = "Setting"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingBinding.inflate(inflater, container, false)

        initializeViews(binding)

        return binding.root
    }

    private fun initializeViews(binding: FragmentSettingBinding) {
        initializeAppBar(binding.appBar)
    }

    private fun initializeAppBar(appBar: AppBar) {
        appBar.setOnBackPressedListener {
            requireActivity().onBackPressed()
        }
    }
}
