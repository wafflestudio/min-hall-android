package com.wafflestudio.snucse.minhall.network.login

import com.wafflestudio.snucse.minhall.model.Token
import com.wafflestudio.snucse.minhall.network.login.dto.LoginRequestDto

class RefreshApiService(private val refreshRetrofitService: RefreshRetrofitService) {
    fun refresh(username: String, password: String): Token? {
        val body = LoginRequestDto(
            username = username,
            password = password,
        )
        val response = refreshRetrofitService.refresh(body).execute()
        return response.body()?.let { Token(it.token) }
    }
}
