package com.workspaceandroid.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    HomeScreen(
        state = viewModel.viewState.collectAsState().value,
        onFloatingButtonClick = {},
        onItemClick = { itemId ->

        }
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeContract.Effect.ShowToast -> TODO()
            }
        }
    }
}

@Composable
fun HomeScreen(
    state: HomeContract.HomeState,
    onFloatingButtonClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {

}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}