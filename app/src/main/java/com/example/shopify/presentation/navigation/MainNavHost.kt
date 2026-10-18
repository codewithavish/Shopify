package com.example.shopify.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shopify.presentation.camera.CameraScreen
import com.example.shopify.presentation.cart.CartScreen
import com.example.shopify.presentation.category.CategoryScreen
import com.example.shopify.presentation.home.HomeScreen
import com.example.shopify.presentation.product.ProductScreen
import com.example.shopify.presentation.productDetail.ProductDetailScreen
import com.example.shopify.presentation.profile.EditProfileScreen
import com.example.shopify.presentation.profile.ProfileScreen
import com.example.shopify.presentation.searchProduct.SearchProductsScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    onLogout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route,
        modifier = Modifier.padding(paddingValues)
    ) {

        composable(Routes.Home.route) {
            HomeScreen(
                onProductClick = { productId ->
                    navController.navigate(
                        Routes.ProductDetail.createRoute(productId)
                    )
                },

                onSearchClick = {
                    navController.navigate(
                        Routes.SearchProducts.route
                    )
                },

                onSeeAllCategories = {
                    navController.navigate(
                        Routes.Category.route
                    )
                },

                onSeeAllProducts = {
                    navController.navigate(
                        Routes.ProductList.createRoute()
                    )
                },

                onCategoryClick = { categoryId ->
                    navController.navigate(
                        Routes.ProductList.createRoute(categoryId)
                    )
                },

                onProfileClick = {
                    navController.navigate(
                        Routes.Profile.route
                    )
                }
            )
        }

        composable(Routes.Category.route) {
            CategoryScreen(
                onCategoryClick = { categoryId ->

                    navController.navigate(
                        Routes.ProductList.createRoute(categoryId)
                    )
                }
            )
        }

        composable(Routes.Cart.route) {
            CartScreen(
                onBackClick = {
                    navController.popBackStack()
                },

                onCheckoutClick = {
                    // Checkout will be added later
                }
            )
        }

        composable(Routes.Profile.route) {
            ProfileScreen(
                onEditProfileClick = {
                    navController.navigate(
                        Routes.EditProfile.route
                    )
                },

                onLogoutSuccess = {
                    onLogout()
                }
            )
        }

        composable(
            route = Routes.ProductList.route,
            arguments = listOf(
                navArgument("categoryId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry
                .arguments
                ?.getString("categoryId")
                ?.toIntOrNull()

            ProductScreen(
                categoryId = categoryId,
                onProductClick = { productId ->
                    navController.navigate(
                        Routes.ProductDetail.createRoute(productId)
                    )
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable(
            route = Routes.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry
                .arguments
                ?.getInt("productId")

            if (productId != null) {
                ProductDetailScreen(
                    productId = productId,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
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


        composable(Routes.EditProfile.route) {
            EditProfileScreen(
                openCamera = {

                    navController.navigate(
                        Routes.Camera.route
                    )
                },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable(Routes.Camera.route) {
            CameraScreen()
        }
    }
}