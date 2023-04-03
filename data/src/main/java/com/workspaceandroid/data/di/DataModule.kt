package com.workspaceandroid.data.di

import android.content.Context
import com.workspaceandroid.data.api.source.IPhrasesNetSource
import com.workspaceandroid.data.common.ITimeHelper
import com.workspaceandroid.data.common.TimeHelper
import com.workspaceandroid.data.database.sharedpreferences.AppSharedPreferences
import com.workspaceandroid.data.database.sharedpreferences.WorkspaceSharedPreferences
import com.workspaceandroid.data.mappers.PhrasesNetMapper
import com.workspaceandroid.data.repositories.CollectionRepository
import com.workspaceandroid.data.repositories.NotificationRepository
import com.workspaceandroid.domain.repositories.ICollectionRepository
import com.workspaceandroid.domain.repositories.INotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideTimeHelper(): ITimeHelper = TimeHelper()

    @Singleton
    @Provides
    fun provideNotificationRepository(
        netSource: IPhrasesNetSource,
        phrasesNetMapper: PhrasesNetMapper
    ): INotificationRepository {
        return NotificationRepository(
            netSource = netSource,
            phrasesNetMapper = phrasesNetMapper
        )
    }
}