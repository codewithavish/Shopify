package com.example.shopify.presentation.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavigation(
    cartItemCount: Int,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val bottomBarRoutes = MainBottomNavItem.items.map {
        it.route
    }

    val showBottomBar = currentRoute in bottomBarRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                MainBottomNavigationBar(
                    currentRoute = currentRoute,
                    cartItemCount = cartItemCount,
                    onItemSelected = { item ->

                        if (currentRoute != item.route) {
                            val destinationExists =
                                navController.popBackStack(
                                    item.route,
                                    false
                                )

                            if (!destinationExists) {
                                navController.navigate(item.route)
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        MainNavHost(
            navController = navController,
            paddingValues = paddingValues,
            onLogout = onLogout
        )
    }
}