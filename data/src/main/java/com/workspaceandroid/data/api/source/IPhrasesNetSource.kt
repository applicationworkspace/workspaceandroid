package com.workspaceandroid.data.api.source

import com.workspaceandroid.data.dto.phrases.PhraseNetDTO

interface IPhrasesNetSource {
    suspend fun fetchUserPhrases(): List<PhraseNetDTO>
}