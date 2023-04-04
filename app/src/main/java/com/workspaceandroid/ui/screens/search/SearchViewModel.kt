package com.workspaceandroid.ui.screens.search

import android.util.Log
import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.domain.interactors.AuthInteractor
import com.workspaceandroid.domain.models.phrase.PhraseInput
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val authInteractor: AuthInteractor) :
    BaseViewModel<SearchContract.Event, SearchContract.SearchState, SearchContract.Effect>() {

    private val userPhrase = PhraseInput()

    override fun setInitialState(): SearchContract.SearchState = SearchContract.SearchState.Idle

    override fun handleEvents(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnPhraseUpdated -> {
                event.phraseBuilder.invoke(userPhrase)
            }
            SearchContract.Event.OnSaveButtonClicked -> {
                Log.d("SearchViewModel", "result: $userPhrase")
            }
        }
    }


}