package com.example.shopify.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopify.core.datastore.SessionManager
import com.example.shopify.presentation.auth.login.LoginScreen
import com.example.shopify.presentation.auth.register.RegisterScreen
import com.example.shopify.presentation.searchProduct.SearchProductsScreen

@Composable
fun AppNavigation(
    sessionManager: SessionManager
) {

    val navController = rememberNavController()

    val startDestination =
        if (sessionManager.isLoggedIn()) {
            Routes.Main.route
        } else {
            Routes.Login.route
        }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(
            route = Routes.Login.route
        ) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(
                        Routes.Main.route
                    ) {
                        popUpTo(
                            Routes.Login.route
                        ) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                },

                onRegisterClick = {
                    navController.navigate(
                        Routes.Register.route
                    )
                }
            )
        }



        composable(
            route = Routes.Register.route
        ) {
            RegisterScreen(
                onRegisterSuccess = {

                    navController.navigate(
                        Routes.Login.route
                    ) {
                        popUpTo(
                            Routes.Register.route
                        ) {
                            inclusive = true
                        }
                    }
                },

                onLoginClick = {
                    navController.navigate(
                        Routes.Login.route
                    ) {
                        popUpTo(
                            Routes.Register.route
                        ) {
                            inclusive = true
                        }
                    }
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Routes.Main.route
        ) {
            MainNavigation(
                // Temporary until Room cart Flow
                // is connected here.
                cartItemCount = 0,
                onLogout = {
                    navController.navigate(
                        Routes.Login.route
                    ) {

                        popUpTo(
                            Routes.Main.route
                        ) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.SearchProducts.route) {
            SearchProductsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onProductClick = { productId ->
                    navController.navigate(
                        Routes.ProductDetail.createRoute(productId)
                    )
                }
            )
        }
    }
}