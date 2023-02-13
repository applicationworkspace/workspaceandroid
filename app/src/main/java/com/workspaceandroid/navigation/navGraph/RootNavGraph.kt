package com.workspaceandroid.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.workspaceandroid.MainScreen
import com.workspaceandroid.navigation.BottomBarScreen
import com.workspaceandroid.navigation.navGraph.Graph.ROOT_ROUTE
import com.workspaceandroid.ui.screens.home.HomeScreen
import com.workspaceandroid.ui.screens.search.SearchScreen
import com.workspaceandroid.ui.screens.settings.SettingsScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Graph.AUTHENTICATION_ROUTE,
        route = ROOT_ROUTE
    ) {

        authNavGraph(navController = navController)
        composable(route = Graph.MAIN_ROUTE) {
            MainScreen()
        }
    }
}

object Graph {
    const val ROOT_ROUTE = "root"
    const val MAIN_ROUTE = "main"
    const val AUTHENTICATION_ROUTE = "authentication"
}