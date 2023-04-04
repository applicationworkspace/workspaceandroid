package com.workspaceandroid.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.workspaceandroid.R
import com.workspaceandroid.domain.models.phrase.PhraseInput
import com.workspaceandroid.ui.theme.*
import com.workspaceandroid.ui.widgets.InputTextField
import com.workspaceandroid.ui.widgets.TextInput
import com.workspaceandroid.ui.widgets.ToolbarComponent

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    SearchScreen(
        state = viewModel.viewState.collectAsState().value,
        onSaveClick = { viewModel.setEvent(SearchContract.Event.OnSaveButtonClicked) },
        phraseBuilder = { builder ->
            viewModel.setEvent(
                SearchContract.Event.OnPhraseUpdated(builder)
            )
        }
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
    phraseBuilder: (PhraseInput.() -> Unit) -> Unit,
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        color = light_gray
    ) {

        ConstraintLayout(
            Modifier.padding(offset_16)
        ) {

            val (toolbar, fieldPhrase, fieldTranslation, fieldDefinition, btnExamples, blockExamples, placeholderBox) = createRefs()
            var textFieldCount by remember { mutableStateOf(0) }

            ToolbarComponent(
                modifier = Modifier
                    .constrainAs(toolbar) {
                        top.linkTo(parent.top, margin = offset_16)
                        start.linkTo(parent.start)
                    },
                text = stringResource(R.string.search_title)
            )

            TextInput(
                modifier = Modifier
                    .constrainAs(fieldPhrase) {
                        top.linkTo(toolbar.bottom, margin = offset_24)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onInputChanged = {
                    phraseBuilder {
                        text = it
                    }
                },
                label = stringResource(id = R.string.create_card_phrase_label),
                placeholderText = stringResource(id = R.string.create_card_phrase_placeholder)
            )

            TextInput(
                modifier = Modifier
                    .constrainAs(fieldTranslation) {
                        top.linkTo(fieldPhrase.bottom, margin = offset_12)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onInputChanged = {
                    phraseBuilder {
                        definition = it
                    }
                },
                label = stringResource(id = R.string.create_card_translation_label),
                placeholderText = stringResource(id = R.string.create_card_translation_placeholder)
            )

            TextInput(
                modifier = Modifier
                    .constrainAs(fieldDefinition) {
                        top.linkTo(fieldTranslation.bottom, margin = offset_12)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onInputChanged = {
                    phraseBuilder {
                        definition = it
                    }
                },
                label = stringResource(id = R.string.create_card_definition_label),
                placeholderText = stringResource(id = R.string.create_card_definition_placeholder)
            )

            Button(
                onClick = {
                    textFieldCount++
                    onSaveClick()
                },
                modifier = Modifier
                    .constrainAs(btnExamples) {
                        top.linkTo(fieldDefinition.bottom, margin = offset_12)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                shape = RoundedCornerShape(radius_8)
            ) {
                Text(
                    modifier = Modifier.padding(top = offset_8, bottom = offset_8),
                    text = stringResource(R.string.create_card_add_example)
                )
            }

            Column(
                modifier = Modifier
                    .constrainAs(blockExamples) {
                        top.linkTo(btnExamples.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
            ) {

                repeat(textFieldCount) {
                    TextInput(
                        modifier = Modifier.padding(top = offset_12),
                        label = "Example #${it + 1}",
                        placeholderText = stringResource(id = R.string.create_card_example_placeholder),
                        onInputChanged = { exampleText ->
                            phraseBuilder {
//                                examples.get(1) = exampleText
//                                text = ""
                            }
                        },
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = elevation_4)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                }
            }


//            Box(Modifier
//                .height(64.dp)
//                .width(64.dp)
//                .background(color = Color.Red))

            Box(modifier = Modifier
                .constrainAs(placeholderBox) {
                    top.linkTo(toolbar.bottom)
                    bottom.linkTo(btnExamples.top)
                }
                .background(color = Color.Red))

        }


    }
}

