package com.wafflestudio.snucse.minhall.view.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.wafflestudio.snucse.minhall.databinding.DialogFragmentWarningBinding

class WarningDialogFragment : BaseDialogFragment() {

    private var _binding: DialogFragmentWarningBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "Notice"

        private const val EXTRA_TITLE = "EXTRA_TITLE"
        private const val EXTRA_BODY = "EXTRA_BODY"
        private const val EXTRA_ACTION = "EXTRA_ACTION"

        fun show(fragmentManager: FragmentManager, title: String, body: String, action: String) =
            WarningDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_TITLE, title)
                    putString(EXTRA_BODY, body)
                    putString(EXTRA_ACTION, action)
                }
            }.show(fragmentManager, TAG)
    }

    private val title by lazy { arguments?.getString(EXTRA_TITLE) ?: "" }
    private val body by lazy { arguments?.getString(EXTRA_BODY) ?: "" }
    private val action by lazy { arguments?.getString(EXTRA_ACTION) ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentWarningBinding.inflate(inflater, container, false)

        initializeViews()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeViews() {
        binding.ctaButton.setOnClickListener {
            dismiss()
        }
        binding.titleText.text = title
        binding.bodyText.text = body
        binding.ctaButton.text = action
    }
}
