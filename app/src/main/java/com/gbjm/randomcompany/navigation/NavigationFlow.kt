package com.gbjm.randomcompany.navigation

sealed class NavigationFlow {
    object UsersFlow : NavigationFlow()
    class DetailsFlow(val userId: String) : NavigationFlow()
}