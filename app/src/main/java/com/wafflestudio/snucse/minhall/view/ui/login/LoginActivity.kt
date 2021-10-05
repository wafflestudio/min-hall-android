package com.wafflestudio.snucse.minhall.view.ui.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import com.wafflestudio.snucse.minhall.databinding.ActivityLoginBinding
import com.wafflestudio.snucse.minhall.network.error.ApiServerException
import com.wafflestudio.snucse.minhall.network.error.ErrorUtil
import com.wafflestudio.snucse.minhall.view.ui.base.BaseActivity
import com.wafflestudio.snucse.minhall.view.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import retrofit2.HttpException

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    companion object {
        fun intent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
    }

    private fun initializeViews() {
        binding.loginButton.setOnClickListener { attemptLogin() }

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

        KeyboardVisibilityEvent.setEventListener(this) { isOpen ->
            binding.logo.visibility = if (isOpen) View.GONE else View.VISIBLE
        }
    }

    private fun webIntent(url: String): Intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }

    private fun attemptLogin() {
        viewModel.login(
            username = binding.snucseIdInput.text.toString(),
            password = binding.passwordInput.text.toString(),
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { binding.progress.visibility = View.VISIBLE }
            .doFinally { binding.progress.visibility = View.GONE }
            .subscribe({ reservationSettings ->
                startActivity(MainActivity.intent(this, reservationSettings))
                finish()
            }, { t ->
                ErrorUtil.showToast(this, t)
            })
            .disposeOnDestroy()
    }
}
