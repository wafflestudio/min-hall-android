package com.wafflestudio.snucse.minhall.di

import android.app.Application
import com.squareup.moshi.Moshi
import com.wafflestudio.snucse.minhall.App
import com.wafflestudio.snucse.minhall.BuildConfig
import com.wafflestudio.snucse.minhall.network.AppInterceptor
import com.wafflestudio.snucse.minhall.network.login.LoginApiService
import com.wafflestudio.snucse.minhall.network.login.LoginRetrofitService
import com.wafflestudio.snucse.minhall.network.seat.SeatApiService
import com.wafflestudio.snucse.minhall.network.seat.SeatRetrofitService
import com.wafflestudio.snucse.minhall.preference.AppPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        application: Application,
        preference: AppPreference,
        moshi: Moshi,
    ): OkHttpClient = OkHttpClient.Builder()
        .also {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
                it.addInterceptor(loggingInterceptor)
            }
        }
        .addNetworkInterceptor(
            AppInterceptor(
                app = application as App,
                preference = preference,
                moshi = moshi,
            )
        )
        .build()

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
    fun provideSeatApiService(retrofit: Retrofit): SeatApiService =
        SeatApiService(retrofit.create(SeatRetrofitService::class.java))
}
