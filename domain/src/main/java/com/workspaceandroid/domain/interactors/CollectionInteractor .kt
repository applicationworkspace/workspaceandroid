package com.workspaceandroid.domain.interactors

import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.repositories.ICollectionRepository
import javax.inject.Inject

class CollectionInteractor @Inject constructor(
    private val collectionRepository: ICollectionRepository
) {

    suspend fun getUserPhrases(): List<Phrase> {
        return collectionRepository.fetchUserPhrases()
    }
}