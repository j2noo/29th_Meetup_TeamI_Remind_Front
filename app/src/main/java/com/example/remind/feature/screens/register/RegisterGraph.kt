package com.example.remind.feature.screens.register

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.remind.app.Screens


fun NavGraphBuilder.RegisterGraph(
    navHostController: NavHostController
) {
    navigation(
        route = Screens.Register.route,
        startDestination = Screens.Register.Login.route
    ) {
        composable(route = Screens.Register.Login.route) {
            LoginScreen(navHostController)
        }
        composable(route = Screens.Register.SelectType.route) {
            SelectTypeScreen(navHostController)
        }
    }
}