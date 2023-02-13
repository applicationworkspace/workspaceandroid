package com.workspaceandroid.data.repositories

import com.workspaceandroid.data.api.source.IPhrasesNetSource
import com.workspaceandroid.data.mappers.PhrasesNetMapper
import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.domain.repositories.ICollectionRepository

class CollectionRepository(
    private val netSource: IPhrasesNetSource,
    private val phrasesNetMapper: PhrasesNetMapper
): ICollectionRepository {

    override suspend fun fetchUserPhrases(): List<Phrase> {
        return netSource.fetchUserPhrases().run(phrasesNetMapper::fromEntityList)
    }
}