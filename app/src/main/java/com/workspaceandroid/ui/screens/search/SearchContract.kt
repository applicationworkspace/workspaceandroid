package com.workspaceandroid.ui.screens.search

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState

class SearchContract {

    sealed class Event : ViewEvent {
        data class OnSearchButtonClicked(val email: String, val password: String) : Event()
    }

    sealed class SearchState: ViewState {
        object Idle : SearchState()
        object Loading : SearchState()
        data class Success(val name : String) : SearchState()
        data class Error(val errorMessage : String) : SearchState()
        data class ValidationError(val errorMessage : String) : SearchState()
    }

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect()

        sealed class Navigation : Effect() {
            data class ToMain(val userSearch: String): Navigation()
        }
    }
}