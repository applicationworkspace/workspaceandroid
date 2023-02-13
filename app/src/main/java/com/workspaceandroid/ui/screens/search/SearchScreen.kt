package com.workspaceandroid.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.workspaceandroid.R
import com.workspaceandroid.ui.theme.offset_12
import com.workspaceandroid.ui.theme.offset_16
import com.workspaceandroid.ui.theme.offset_32
import com.workspaceandroid.ui.widgets.InputTextField
import com.workspaceandroid.ui.widgets.ToolbarComponent

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    SearchScreen(
        state = viewModel.viewState.value,
        onSaveClick = { },
        onPhraseInput = { }
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchContract.Effect.Navigation.ToMain -> TODO()
                is SearchContract.Effect.ShowToast -> TODO()
            }
        }
    }
}

@Composable
fun SearchScreen(
    state: SearchContract.SearchState,
    onSaveClick: () -> Unit,
    onPhraseInput: (String) -> Unit
) {

    val inputPhrase = rememberSaveable { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ConstraintLayout(
            Modifier.padding(offset_16)
        ) {
            val (toolbar, inputPhraseField) = createRefs()

            ToolbarComponent(
                modifier = Modifier
                    .constrainAs(toolbar) {
                        top.linkTo(parent.top, margin = offset_32)
                        start.linkTo(parent.start)
                    },
                text = stringResource(R.string.search_title)
            )

            InputTextField(
                modifier = Modifier
                    .constrainAs(inputPhraseField) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                value = inputPhrase.value,
                label = stringResource(id = R.string.auth_email),
                onInputChanged = {
                    inputPhrase.value = it
                },
                inputType = KeyboardType.Email,
                placeholderText = stringResource(id = R.string.auth_email_hint)
            )
        }
    }
}

