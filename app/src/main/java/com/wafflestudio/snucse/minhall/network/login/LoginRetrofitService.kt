package com.wafflestudio.snucse.minhall.network.login

import com.wafflestudio.snucse.minhall.network.login.dto.LoginRequestDto
import com.wafflestudio.snucse.minhall.network.login.dto.LoginResponseDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitService {

    @POST("/login")
    fun login(@Body body: LoginRequestDto): Single<LoginResponseDto>
}
