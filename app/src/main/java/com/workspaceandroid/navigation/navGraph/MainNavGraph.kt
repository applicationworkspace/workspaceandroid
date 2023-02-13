package com.workspaceandroid.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.workspaceandroid.navigation.BottomBarScreen
import com.workspaceandroid.ui.screens.collection.CollectionScreen
import com.workspaceandroid.ui.screens.home.HomeScreen
import com.workspaceandroid.ui.screens.search.SearchScreen
import com.workspaceandroid.ui.screens.settings.SettingsScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.MAIN_ROUTE,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen(navController)
        }
        composable(route = BottomBarScreen.Collection.route) {
            CollectionScreen(navController)
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(navController)
        }
//        detailsNavGraph(navController = navController)
        authNavGraph(navController = navController)
    }
}

//fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
//    navigation(
//        route = Graph.SEARCH_ROUTE,
//        startDestination = DetailsScreen.Information.route
//    ) {
//        composable(route = DetailsScreen.Information.route) {
//            ScreenContent(name = DetailsScreen.Information.route) {
//                navController.navigate(DetailsScreen.Overview.route)
//            }
//        }
//        composable(route = DetailsScreen.Overview.route) {
//            ScreenContent(name = DetailsScreen.Overview.route) {
//                navController.popBackStack(
//                    route = DetailsScreen.Information.route,
//                    inclusive = false
//                )
//            }
//        }
//    }
//}

sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "information")
    object Overview : DetailsScreen(route = "overview")
}