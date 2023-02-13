package com.workspaceandroid.ui.screens.collection

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState
import com.workspaceandroid.domain.models.phrase.Phrase

class CollectionContract {

    sealed class Event : ViewEvent {
        data class AddButtonClicked(val email: String, val password: String) : Event()
        data class OnItemSelected(val phraseId: Int) : Event()
    }

    sealed class CollectionState : ViewState {
        object Loading : CollectionState()
        data class Success(
            val phrases: List<Phrase>,
            val expandedCardIds: List<Int>
        ) : CollectionState()

        data class Error(val errorMessage: String) : CollectionState()
    }

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect()
    }
}