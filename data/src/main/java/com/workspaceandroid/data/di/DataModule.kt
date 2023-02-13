package com.workspaceandroid.data.di

import android.content.Context
import com.workspaceandroid.data.common.ITimeHelper
import com.workspaceandroid.data.common.TimeHelper
import com.workspaceandroid.data.database.sharedpreferences.AppSharedPreferences
import com.workspaceandroid.data.database.sharedpreferences.WorkspaceSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideTimeHelper(): ITimeHelper = TimeHelper()
}