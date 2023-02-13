package com.workspaceandroid.data.api

import android.util.Log
import com.workspaceandroid.data.api.service.NetworkApiService
import com.workspaceandroid.data.database.sharedpreferences.AppSharedPreferences
import javax.inject.Inject

class RefreshTokenService @Inject constructor(
    private val sharedPreferences: AppSharedPreferences,
    private val refreshTokenApiService: NetworkApiService
) {

    suspend fun refreshToken() {
        sharedPreferences.getAuthToken()
        val newToken = refreshTokenApiService.refreshToken()
        Log.d("REFRESH_TOKEN", "refreshToken: $newToken")
    }

}