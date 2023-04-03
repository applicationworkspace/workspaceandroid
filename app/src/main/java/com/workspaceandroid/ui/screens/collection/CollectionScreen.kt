package com.workspaceandroid.ui.screens.collection

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.workspaceandroid.R
import com.workspaceandroid.domain.models.phrase.Phrase
import com.workspaceandroid.ui.theme.blue
import com.workspaceandroid.ui.theme.elevation_4
import com.workspaceandroid.ui.theme.elevation_6
import com.workspaceandroid.ui.theme.icon_size_64
import com.workspaceandroid.ui.theme.offset_12
import com.workspaceandroid.ui.theme.offset_16
import com.workspaceandroid.ui.theme.offset_4
import com.workspaceandroid.ui.theme.offset_8
import com.workspaceandroid.ui.theme.orange
import com.workspaceandroid.ui.theme.radius_16
import com.workspaceandroid.ui.theme.white
import com.workspaceandroid.ui.widgets.ToolbarComponent
import com.workspaceandroid.utils.EXPAND_ANIMATION_DURATION
import com.workspaceandroid.utils.EXPANSTION_TRANSITION_DURATION
import com.workspaceandroid.utils.noRippleClickable

@Composable
fun CollectionScreen(
    navController: NavController,
    viewModel: CollectionViewModel = hiltViewModel()
) {

    CollectionScreen(
        state = viewModel.viewState.collectAsState().value.collectionState,
        onFloatingButtonClick = {},
        onItemClick = { itemId ->
            viewModel.setEvent(CollectionContract.Event.OnItemSelected(itemId))
        }
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CollectionContract.Effect.ShowToast -> TODO()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(
    state: CollectionContract.CollectionState,
    onFloatingButtonClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = onFloatingButtonClick,
            modifier = Modifier.size(icon_size_64)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "FloatingButton"
            )
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ToolbarComponent(
                modifier = Modifier.padding(start = offset_16, top = offset_16, bottom = offset_16),
                text = stringResource(R.string.collection_title),
            )
            if (state is CollectionContract.CollectionState.Success) {

                OverviewSection(cardsSize = state.phrases.size)

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(offset_16)
                ) {
                    items(state.phrases) { phrase ->
                        ExpandableCard(
                            phrase = phrase,
                            onCardClick = { onItemClick(phrase.id) },
                            expanded = state.expandedCardIds.contains(phrase.id),
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableCard(
    phrase: Phrase,
    onCardClick: () -> Unit,
    expanded: Boolean,
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }

    val transition = updateTransition(transitionState, label = "")
    val cardElevation by transition.animateDp({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 24.dp else 4.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = EXPAND_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    }, label = "") {
        if (expanded) 0.dp else 16.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 180f else 0f
    }
//    val contentColour = remember {
//        Color(ContextCompat.getColor(context, R.color.colowrDayNightPurple))
//    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = offset_8
            ),
        colors = CardDefaults.cardColors(containerColor = white)
    ) {
        Column {
            Box(
                modifier = Modifier.noRippleClickable { onCardClick() }
            ) {
                CardTitle(title = phrase.text)
                CardArrow(
                    modifier = Modifier.align(Alignment.TopEnd),
                    degrees = arrowRotationDegree,
                    onClick = onCardClick
                )
            }
            ExpandableContent(phrase = phrase, visible = expanded)
        }
    }
}

@Composable
fun CardArrow(
    modifier: Modifier,
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
            )
        }
    )
}

@Composable
fun CardTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(offset_16),
        textAlign = TextAlign.Left
    )
}

@Composable
fun ExpandableContent(
    phrase: Phrase,
    visible: Boolean = true,
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        ) + fadeOut(
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }

    AnimatedVisibility(
        visible = visible,
        enter = enterTransition,
        exit = exitTransition
    ) {
        Column(modifier = Modifier.padding(offset_8)) {
            CustomChipTitle(
                text = stringResource(R.string.collection_definition),
                color = blue.copy(alpha = 0.4f)
            )
            Text(phrase.definition)
            CustomChipTitle(
                text = stringResource(R.string.collection_definition),
                color = orange.copy(alpha = 0.4f)
            )
            val phraseExamples = phrase.examples.joinToString(separator = ", ")
            Text("Examples: $phraseExamples")
        }
    }
}

@Composable
fun CustomChipTitle(
    text: String,
    color: Color
) {
    Text(
        modifier = Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(vertical = offset_8, horizontal = offset_12),
        text = text
    )
}

@Composable
fun OverviewSection(
    cardsSize: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = offset_16)
    ) {
        Text(
            text = stringResource(R.string.collection_overview),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(offset_8))
        Row {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = orange),
                shape = RoundedCornerShape(radius_16),
                elevation = CardDefaults.cardElevation(defaultElevation = elevation_4)
            ) {
                Column(modifier = Modifier.padding(offset_12)) {
                    Text("Added") //TODO resources
                    Text(text = "$cardsSize", fontWeight = FontWeight.Bold)
                    Text("cards in collection")
                }
            }
            Spacer(modifier = Modifier.width(offset_12))
            Card(
                modifier = Modifier.weight(2f),
                colors = CardDefaults.cardColors(containerColor = blue),
                shape = RoundedCornerShape(radius_16)
            ) {
                Column(modifier = Modifier.padding(offset_12)) {
                    Text("Studied") //TODO resources
                    Text(text = "5", fontWeight = FontWeight.Bold)
                    Text("cards today")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CollectionScreenPreview() {
    CollectionScreen(navController = rememberNavController())
}