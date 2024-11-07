package com.example.googlehealthconnect.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.googlehealthconnect.data.HealthRepo
import com.example.googlehealthconnect.screens.App
import com.example.googlehealthconnect.screens.Settings

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    repo: HealthRepo
) {
    NavHost(
        navController = navController,
        startDestination = "Start",
        modifier = modifier
    ) {
        composable(route = "Start") {
            App(
                navigateToSettings = {
                    navController.navigate("Settings")
                },
                repo = repo
            )
        }
        composable(route = "Settings") {
            Settings(
                navigateBack = {
                    navController.popBackStack()
                },
                repo = repo
            )
        }

    }
}