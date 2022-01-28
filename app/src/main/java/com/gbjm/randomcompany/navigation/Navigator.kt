package com.gbjm.randomcompany.navigation

import androidx.navigation.NavController
import com.gbjm.randomcompany.MainNavGraphDirections

class Navigator {
    lateinit var navController: NavController

    // navigate on main thread or nav component crashes sometimes
    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        NavigationFlow.UsersFlow -> navController.navigate(MainNavGraphDirections.actionGlobalUsersFlow())
        is NavigationFlow.DetailsFlow -> navController.navigate(MainNavGraphDirections.actionGlobalUserDetailFlow(navigationFlow.userId))
    }
}