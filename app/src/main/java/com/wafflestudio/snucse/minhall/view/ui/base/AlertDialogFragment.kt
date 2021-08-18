package com.wafflestudio.snucse.minhall.view.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import com.wafflestudio.snucse.minhall.databinding.DialogFragmentAlertBinding

class AlertDialogFragment(
    @StringRes private val bodyResId: Int,
    private val onConfirm: () -> Unit,
    private val onCancel: (() -> Unit)?,
) : BaseDialogFragment() {

    private var _binding: DialogFragmentAlertBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "Notice"

        private const val EXTRA_BODY = "EXTRA_BODY"

        fun show(
            fragmentManager: FragmentManager,
            @StringRes bodyResId: Int,
            onConfirm: () -> Unit,
            onCancel: (() -> Unit)?,
        ) = AlertDialogFragment(
            bodyResId,
            onConfirm,
            onCancel
        )
            .show(fragmentManager, TAG)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentAlertBinding.inflate(inflater, container, false)

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
        binding.okButton.setOnClickListener {
            onConfirm()
            dismiss()
        }
        onCancel?.let { onCancel ->
            binding.cancelButton.setOnClickListener {
                onCancel()
                dismiss()
            }
        } ?: run {
            binding.cancelButton.visibility = View.GONE
        }

        binding.bodyText.text = getString(bodyResId)
    }
}
