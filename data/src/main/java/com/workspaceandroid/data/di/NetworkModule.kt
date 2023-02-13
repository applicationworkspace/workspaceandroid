package com.workspaceandroid.data.di

import com.workspaceandroid.data.api.RefreshTokenService
import com.workspaceandroid.data.api.TokenInterceptor
import com.workspaceandroid.data.api.service.NetworkApiService
import com.workspaceandroid.data.api.source.IAuthNetSource
import com.workspaceandroid.data.api.source.IPhrasesNetSource
import com.workspaceandroid.data.api.source.impl.AuthNetSource
import com.workspaceandroid.data.api.source.impl.PhrasesNetSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(
                "http://10.0.2.2:8080"
            )
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkApiService(retrofit: Retrofit): NetworkApiService {
        return retrofit.create(NetworkApiService::class.java)
    }


    // net sources

    @Provides
    @Singleton
    fun provideAuthNetSource(
        networkApiService: NetworkApiService,
        tokenService: RefreshTokenService
    ): IAuthNetSource {
        return AuthNetSource(
            networkApiService = networkApiService,
            refreshTokenService = tokenService
        )
    }

    @Provides
    @Singleton
    fun providePhrasesNetSource(
        networkApiService: NetworkApiService,
        tokenService: RefreshTokenService
    ): IPhrasesNetSource {
        return PhrasesNetSource(
            networkApiService = networkApiService,
            refreshTokenService = tokenService
        )
    }


//    @Singleton
//    @Provides
//    fun provideRequestHeaderInterceptor(preferences: AppSharedPreferences): HeaderTokenInterceptor {
//        return HeaderTokenInterceptor(preferences)
//    }
}