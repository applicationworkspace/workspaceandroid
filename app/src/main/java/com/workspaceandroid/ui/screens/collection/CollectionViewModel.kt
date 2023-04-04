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
    private val timeHelper: ITimeHelper,
) : BaseViewModel<CollectionContract.Event, CollectionContract.State, CollectionContract.Effect>() {

    private var userPhrases: List<Phrase> = emptyList()

    init {
        fetchUserCollection()
    }

    override fun setInitialState(): CollectionContract.State =
        CollectionContract.State(isLoading = true)

    override fun handleEvents(event: CollectionContract.Event) {
        when (event) {
            is CollectionContract.Event.AddButtonClicked -> fetchUserCollection()
            is CollectionContract.Event.OnItemSelected -> updateExpandedCards(event.selectedPhrase)
        }
    }

    private fun updateExpandedCards(selectedPhrase: Phrase) {
        setState {
            userPhrases = phrases.map {
                if (it.id == selectedPhrase.id) it.copy(isExpanded = !it.isExpanded)
                else it
            }.toMutableList()
            copy(phrases = userPhrases)
        }
    }

    private fun fetchUserCollection() {
        if (userPhrases.isNotEmpty()) {
            return
        }
        viewModelScope.launch {
            userPhrases = collectionInteractor.getUserPhrases()
            setState {
                copy(phrases = userPhrases, isLoading = false)
            }
        }
    }
}