package com.wafflestudio.snucse.minhall.di

import android.app.Application
import com.squareup.moshi.Moshi
import com.wafflestudio.snucse.minhall.App
import com.wafflestudio.snucse.minhall.BuildConfig
import com.wafflestudio.snucse.minhall.network.AppInterceptor
import com.wafflestudio.snucse.minhall.network.HeaderUtil
import com.wafflestudio.snucse.minhall.network.login.LoginApiService
import com.wafflestudio.snucse.minhall.network.login.LoginRetrofitService
import com.wafflestudio.snucse.minhall.network.login.RefreshApiService
import com.wafflestudio.snucse.minhall.network.login.RefreshRetrofitService
import com.wafflestudio.snucse.minhall.network.reservation.ReservationApiService
import com.wafflestudio.snucse.minhall.network.reservation.ReservationRetrofitService
import com.wafflestudio.snucse.minhall.network.seat.SeatApiService
import com.wafflestudio.snucse.minhall.network.seat.SeatRetrofitService
import com.wafflestudio.snucse.minhall.preference.AppPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import javax.net.ssl.HttpsURLConnection

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        authenticator: Authenticator,
        preference: AppPreference,
        moshi: Moshi,
    ): OkHttpClient = OkHttpClient.Builder()
        .addLoggingInterceptor()
        .addNetworkInterceptor(
            AppInterceptor(
                preference = preference,
                moshi = moshi,
            )
        )
        .authenticator(authenticator)
        .build()

    @Provides
    @Singleton
    fun provideAuthenticator(
        application: Application,
        preference: AppPreference,
        refreshApiService: RefreshApiService,
    ): Authenticator = Authenticator { route, response ->
        if (response.code == HttpsURLConnection.HTTP_UNAUTHORIZED) {
            refreshApiService.refresh(
                preference.username,
                preference.password,
            )?.let { token ->
                preference.token = token.value
                val builder = response.request.newBuilder()
                HeaderUtil.addAuthorizationHeader(builder, token)
                builder.build()
            } ?: run {
                (application as App).signOut()
                null
            }
        } else null
    }

    private fun OkHttpClient.Builder.addLoggingInterceptor(): OkHttpClient.Builder {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            addInterceptor(loggingInterceptor)
        }
        return this
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        moshi: Moshi,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService =
        LoginApiService(retrofit.create(LoginRetrofitService::class.java))

    @Provides
    @Singleton
    fun provideRefreshApiService(moshi: Moshi): RefreshApiService {
        val client = OkHttpClient.Builder()
            .addLoggingInterceptor()
            .build()

        val refreshRetrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val refreshRetrofitService = refreshRetrofit.create(RefreshRetrofitService::class.java)

        return RefreshApiService(refreshRetrofitService)
    }

    @Provides
    @Singleton
    fun provideSeatApiService(retrofit: Retrofit): SeatApiService =
        SeatApiService(retrofit.create(SeatRetrofitService::class.java))

    @Provides
    @Singleton
    fun provideReservationApiService(retrofit: Retrofit): ReservationApiService =
        ReservationApiService(retrofit.create(ReservationRetrofitService::class.java))
}
