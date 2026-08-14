package com.example.lactare.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lactare.ui.screens.BancosScreen
import com.example.lactare.ui.screens.CadastroScreen
import com.example.lactare.ui.screens.ChatScreen
import com.example.lactare.ui.screens.DashboardScreen

@Composable
fun LactareNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Cadastro.route
    ) {
        composable(Routes.Cadastro.route) {
            CadastroScreen(
                onGoBancos = { navController.navigate(Routes.Bancos.route) },
                onGoDashboard = { navController.navigate(Routes.Dashboard.route) }
            )
        }

        composable(Routes.Bancos.route) {
            BancosScreen(
                onBack = { navController.popBackStack() },
                onGoCadastro = {
                    navController.navigate(Routes.Cadastro.route) {
                        popUpTo(Routes.Cadastro.route) { inclusive = true }
                    }
                },
                onGoChat = { navController.navigate(Routes.Chat.route) },
                onGoDashboard = { navController.navigate(Routes.Dashboard.route) }
            )
        }

        composable(Routes.Chat.route) {
            ChatScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.Dashboard.route) {
            DashboardScreen(
                onBack = { navController.popBackStack() },
                onGoBancos = { navController.navigate(Routes.Bancos.route) }
            )
        }
    }
}