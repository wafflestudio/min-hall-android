package com.wafflestudio.snucse.minhall.network

import android.app.Application
import com.squareup.moshi.Moshi
import com.wafflestudio.snucse.minhall.App
import com.wafflestudio.snucse.minhall.network.error.ApiServerException
import com.wafflestudio.snucse.minhall.network.error.ErrorResponseBody
import com.wafflestudio.snucse.minhall.network.login.RefreshApiService
import com.wafflestudio.snucse.minhall.preference.AppPreference
import okhttp3.*
import timber.log.Timber
import javax.net.ssl.HttpsURLConnection

class AppInterceptor(
    private val application: Application,
    private val preference: AppPreference,
    private val moshi: Moshi,
    private val refreshApiService: RefreshApiService,
) : Interceptor, Authenticator {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        preference.token.takeIf { it.isNotBlank() }?.let { token ->
            builder.addAuthorizationHeader(token)
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
                Timber.e("Server error: $body.")
                throw ApiServerException(body)
            }
        }
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == HttpsURLConnection.HTTP_UNAUTHORIZED) {
            refreshApiService.refresh(
                preference.username,
                preference.password,
            )?.let { token ->
                preference.token = token.value
                val builder = response.request.newBuilder()
                builder.addAuthorizationHeader(token.value)
                return builder.build()
            } ?: (application as App).signOut()
        }
        return null
    }

    private fun Request.Builder.addAuthorizationHeader(token: String) {
        addHeader(AUTHORIZATION_HEADER, "Bearer $token")
    }
}
