package com.wafflestudio.snucse.minhall.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.databinding.AppBarBinding
import com.wafflestudio.snucse.minhall.util.dp

class AppBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val binding = AppBarBinding.inflate(LayoutInflater.from(context), this)

    private var onBackPressedListener: (() -> Unit)? = null
    private var onSettingsPressedListener: (() -> Unit)? = null

    init {
        setBackgroundResource(R.color.white)
        elevation = 2.dp.toFloat()
        initializeViews()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(61.dp, MeasureSpec.EXACTLY))
    }

    fun setOnBackPressedListener(onBackPressedListener: (() -> Unit)?) {
        binding.backButton.visibility =
            if (onBackPressedListener == null) View.GONE else View.VISIBLE
        this.onBackPressedListener = onBackPressedListener
    }

    fun setOnSettingsPressedListener(onSettingsPressedListener: (() -> Unit)?) {
        binding.settingsButton.visibility =
            if (onSettingsPressedListener == null) View.GONE else View.VISIBLE
        this.onSettingsPressedListener = onSettingsPressedListener
    }

    private fun initializeViews() {
        binding.backButton.visibility = View.GONE
        binding.backButton.setOnClickListener { onBackPressedListener?.invoke() }
        binding.settingsButton.visibility = View.GONE
        binding.settingsButton.setOnClickListener { onSettingsPressedListener?.invoke() }
    }
}
