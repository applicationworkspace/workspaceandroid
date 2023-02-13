package com.workspaceandroid.ui.screens.collection

import androidx.lifecycle.viewModelScope
import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.data.common.ITimeHelper
import com.workspaceandroid.domain.interactors.CollectionInteractor
import com.workspaceandroid.domain.models.phrase.Phrase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val collectionInteractor: CollectionInteractor,
    private val timeHelper: ITimeHelper
) : BaseViewModel<CollectionContract.Event, CollectionContract.CollectionState, CollectionContract.Effect>() {

    private val expandedCardIds = mutableListOf<Int>()
    private var userPhrases: List<Phrase> = emptyList()

    init {
        fetchUserCollection()
    }

    override fun setInitialState(): CollectionContract.CollectionState = CollectionContract.CollectionState.Loading

    override fun handleEvents(event: CollectionContract.Event) {
        when (event) {
            is CollectionContract.Event.AddButtonClicked -> fetchUserCollection()
            is CollectionContract.Event.OnItemSelected -> updateExpandedCards(event.phraseId)
        }
    }

    private fun updateExpandedCards(cardId: Int) {
        if (expandedCardIds.contains(cardId)) expandedCardIds.remove(cardId)
        else expandedCardIds.add(cardId)
        setState {
            CollectionContract.CollectionState.Loading
        }
        setState {
            CollectionContract.CollectionState.Success(
                userPhrases,
                expandedCardIds
            )
        }
    }

    private fun fetchUserCollection() {
        viewModelScope.launch {
            userPhrases = collectionInteractor.getUserPhrases()
            setState {
                CollectionContract.CollectionState.Success(
                    userPhrases,
                    expandedCardIds
                )
            }
        }
    }
}