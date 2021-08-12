package com.wafflestudio.snucse.minhall.network.login

import com.wafflestudio.snucse.minhall.model.Token
import com.wafflestudio.snucse.minhall.network.login.dto.LoginRequestDto
import io.reactivex.rxjava3.core.Single

class LoginApiService(private val loginRetrofitService: LoginRetrofitService) {

    fun login(username: String, password: String): Single<Token> {
        val body = LoginRequestDto(
            username = username,
            password = password,
        )
        return loginRetrofitService.login(body)
            .map { Token(it.token) }
    }
}
