package com.workspaceandroid.domain.repositories

import com.workspaceandroid.domain.models.phrase.Phrase

interface ICollectionRepository {
    suspend fun fetchUserPhrases(): List<Phrase>
}