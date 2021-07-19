package com.wafflestudio.snucse.minhall.view.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.wafflestudio.snucse.minhall.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    companion object {
        fun intent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
    }

    private fun initializeViews() {
        binding.loginButton.setOnClickListener {
            Toast.makeText(this, "Login pressed", Toast.LENGTH_SHORT).show()
        }

        binding.passwordInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.loginButton.performClick()
                true
            } else {
                false
            }
        }
    }
}
