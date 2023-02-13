package com.workspaceandroid.data.api.service

import com.workspaceandroid.data.dto.auth.RefreshTokenNetDTO
import retrofit2.http.POST

interface CollectionApiService {

    @POST("api/products")
    suspend fun getPersonalCollection(): RefreshTokenNetDTO

}