package com.wafflestudio.snucse.minhall.view.ui.login

import androidx.lifecycle.ViewModel
import com.wafflestudio.snucse.minhall.model.Token
import com.wafflestudio.snucse.minhall.network.login.LoginApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(
    private val loginApiService: LoginApiService,
) : ViewModel() {

    fun login(username: String, password: String): Single<Token> {
        return loginApiService.login(username, password)
    }
}
