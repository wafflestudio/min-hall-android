package com.wafflestudio.snucse.minhall.view.ui.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafflestudio.snucse.minhall.App
import com.wafflestudio.snucse.minhall.R
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

        binding.contactContainer.setOnClickListener {
            dial(getString(R.string.contact_number))
        }

        binding.logoutContainer.setOnClickListener {
            (requireActivity().application as App).signOut()
        }
    }

    private fun initializeAppBar(appBar: AppBar) {
        appBar.setOnBackPressedListener {
            requireActivity().onBackPressed()
        }
    }

    private fun dial(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phoneNumber}"))
        startActivity(dialIntent)
    }
}
