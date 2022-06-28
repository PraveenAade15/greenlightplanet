package com.example.greenlight.di

import com.example.greenlight.api.ApiService
import com.example.greenlight.utils.Constant.BASE_URL
import com.example.greenlight.utils.Constant.TIMEOUT_TIME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
//
@InstallIn(SingletonComponent::class)
@Module
class NetWorkModule {
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                    ).apply { HttpLoggingInterceptor.Level.BODY }
                    .connectTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return builder.create(ApiService::class.java)


    }
}