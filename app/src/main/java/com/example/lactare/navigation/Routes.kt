package com.example.lactare.navigation

sealed class Routes(val route: String) {
    data object Cadastro : Routes("cadastro")
    data object Bancos : Routes("bancos")
    data object Chat : Routes("chat")
    data object Dashboard : Routes("dashboard")
}
