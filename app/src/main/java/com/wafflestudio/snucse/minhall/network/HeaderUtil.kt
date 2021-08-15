package com.wafflestudio.snucse.minhall.network

import com.wafflestudio.snucse.minhall.model.Token
import okhttp3.Request

object HeaderUtil {

    private const val AUTHORIZATION_HEADER = "Authorization"

    fun addAuthorizationHeader(builder: Request.Builder, token: String) {
        builder.addHeader(AUTHORIZATION_HEADER, "Bearer $token")
    }

    fun addAuthorizationHeader(builder: Request.Builder, token: Token) {
        builder.addHeader(AUTHORIZATION_HEADER, "Bearer ${token.value}")
    }
}
