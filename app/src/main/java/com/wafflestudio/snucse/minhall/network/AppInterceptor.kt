package com.wafflestudio.snucse.minhall.network

import com.squareup.moshi.Moshi
import com.wafflestudio.snucse.minhall.App
import com.wafflestudio.snucse.minhall.network.error.ApiServerException
import com.wafflestudio.snucse.minhall.network.error.ErrorResponseBody
import com.wafflestudio.snucse.minhall.preference.AppPreference
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.net.ssl.HttpsURLConnection

class AppInterceptor(
    private val app: App,
    private val preference: AppPreference,
    private val moshi: Moshi,
) : Interceptor {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        addAuthorizationHeader(builder)

        return chain.proceed(builder.build()).also { response ->
            if (response.code == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                handleUnauthorized()
            } else if (response.code !in 200..300) {
                handleError(response)
            }
        }
    }

    private fun addAuthorizationHeader(builder: Request.Builder) {
        preference.token.takeIf { it.isNotBlank() }?.let { token ->
            builder.addHeader(AUTHORIZATION_HEADER, "Bearer $token")
        }
    }

    private fun handleUnauthorized() {
        if (preference.token.isNotBlank()) {
            app.signOut()
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
