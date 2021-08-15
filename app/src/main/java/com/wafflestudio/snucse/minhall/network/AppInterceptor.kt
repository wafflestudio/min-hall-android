package com.wafflestudio.snucse.minhall.network

import com.squareup.moshi.Moshi
import com.wafflestudio.snucse.minhall.network.error.ApiServerException
import com.wafflestudio.snucse.minhall.network.error.ErrorResponseBody
import com.wafflestudio.snucse.minhall.preference.AppPreference
import okhttp3.Interceptor
import okhttp3.Response

class AppInterceptor(
    private val preference: AppPreference,
    private val moshi: Moshi,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        preference.token.takeIf { it.isNotBlank() }?.let { token ->
            HeaderUtil.addAuthorizationHeader(builder, token)
        }

        return chain.proceed(builder.build()).also { response ->
            if (response.code !in 200..300) {
                handleError(response)
            }
        }
    }

    private fun handleError(response: Response) {
        response.body?.string()?.let {
            moshi.adapter(ErrorResponseBody::class.java).fromJson(it)?.let { body ->
                throw ApiServerException(body)
            }
        }
    }
}
