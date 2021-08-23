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
            val body = try {
                moshi.adapter(ErrorResponseBody::class.java).fromJson(it)
            } catch (t: Throwable) {
                throw ApiServerException(ErrorResponseBody(null, null, null))
            }
            body?.let { throw ApiServerException(body) }
        }
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        try {
            refreshApiService.refresh(
                preference.username,
                preference.password,
            )?.let { token ->
                preference.token = token.value
                val builder = response.request.newBuilder()
                builder.addAuthorizationHeader(token.value)
                return builder.build()
            } ?: (application as App).signOut()
        } catch (t: Throwable) {
            Timber.e(t)
        }
        return null
    }

    private fun Request.Builder.addAuthorizationHeader(token: String) {
        header(AUTHORIZATION_HEADER, "Bearer $token")
    }
}
