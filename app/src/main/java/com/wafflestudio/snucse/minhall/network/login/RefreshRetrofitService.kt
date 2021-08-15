package com.wafflestudio.snucse.minhall.network.login

import com.wafflestudio.snucse.minhall.network.login.dto.LoginRequestDto
import com.wafflestudio.snucse.minhall.network.login.dto.LoginResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshRetrofitService {

    @POST("/login")
    fun refresh(@Body body: LoginRequestDto): Call<LoginResponseDto>
}
