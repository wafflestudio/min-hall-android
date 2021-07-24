package com.wafflestudio.snucse.minhall.view.ui.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.wafflestudio.snucse.minhall.databinding.ActivityLoginBinding
import com.wafflestudio.snucse.minhall.view.ui.main.MainActivity
import com.wafflestudio.snucse.minhall.view.ui.base.BaseActivity

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
            startActivity(MainActivity.intent(this))
            finish()
        }

        binding.passwordInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.loginButton.performClick()
                true
            } else {
                false
            }
        }

        binding.registerButton.setOnClickListener {
            startActivity(webIntent("https://id.snucse.org/verify"))
        }

        binding.findPasswordButton.setOnClickListener {
            startActivity(webIntent("https://id.snucse.org/password-reset"))
        }
    }

    private fun webIntent(url: String): Intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
}
