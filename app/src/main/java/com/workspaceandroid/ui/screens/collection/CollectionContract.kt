package com.workspaceandroid.ui.screens.collection

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState
import com.workspaceandroid.domain.models.phrase.Phrase

class CollectionContract {

    sealed class Event : ViewEvent {
        data class AddButtonClicked(val email: String, val password: String) : Event()
        data class OnItemSelected(val selectedPhrase: Phrase) : Event()
    }

//    data class State(
//        val collectionState: CollectionState
//        val isLoading = true
//    ) : ViewState

    data class State(
        val phrases: List<Phrase> = emptyList(),
        val isLoading: Boolean = false
    ) : ViewState

//    sealed class CollectionState {
//        object Loading : CollectionState()
//        data class Success(
//
//        ) : CollectionState()
//
//        data class Error(val errorMessage: String) : CollectionState()
//    }

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect()
    }
}