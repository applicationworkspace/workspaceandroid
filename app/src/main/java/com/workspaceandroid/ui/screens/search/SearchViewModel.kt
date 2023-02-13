package com.workspaceandroid.ui.screens.search

import androidx.lifecycle.viewModelScope
import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.domain.interactors.AuthInteractor
import com.workspaceandroid.ui.screens.login.LoginContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(private val authInteractor: AuthInteractor)
    : BaseViewModel<SearchContract.Event, SearchContract.SearchState, SearchContract.Effect>() {

    override fun setInitialState(): SearchContract.SearchState = SearchContract.SearchState.Idle

    override fun handleEvents(event: SearchContract.Event) = when (event) {
        is SearchContract.Event.OnSearchButtonClicked ->  {

        }
    }
}