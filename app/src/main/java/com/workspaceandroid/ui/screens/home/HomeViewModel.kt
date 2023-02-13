package com.workspaceandroid.ui.screens.home

import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.data.common.ITimeHelper
import com.workspaceandroid.domain.models.phrase.Phrase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val timeHelper: ITimeHelper
) : BaseViewModel<HomeContract.Event, HomeContract.HomeState, HomeContract.Effect>() {

    override fun setInitialState(): HomeContract.HomeState = HomeContract.HomeState.Loading

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.onButtonClicked -> TODO()
        }
    }

}