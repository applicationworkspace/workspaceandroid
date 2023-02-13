package com.workspaceandroid.data.api.service

import com.workspaceandroid.data.dto.auth.AuthResponseNetDTO
import com.workspaceandroid.data.dto.auth.AuthorizationNetDTO
import com.workspaceandroid.data.dto.auth.RefreshTokenNetDTO
import com.workspaceandroid.data.dto.phrases.PhraseNetDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkApiService {

    @POST("api/auth/signin")
    suspend fun auth(@Body model: AuthorizationNetDTO): AuthResponseNetDTO

    @POST("api/auth/refresh")
    suspend fun refreshToken(): RefreshTokenNetDTO

    @GET("api/phrases")
    suspend fun getPersonalCollection(): List<PhraseNetDTO>

}