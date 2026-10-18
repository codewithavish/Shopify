package com.example.shopify.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MainBottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {

    data object Home : MainBottomNavItem(
        route = Routes.Home.route,
        label = "Home",
        icon = Icons.Outlined.Home
    )

    data object Category : MainBottomNavItem(
        route = Routes.Category.route,
        label = "Categories",
        icon = Icons.Outlined.GridView
    )

    data object Cart : MainBottomNavItem(
        route = Routes.Cart.route,
        label = "Cart",
        icon = Icons.Outlined.ShoppingCart
    )

    data object Profile : MainBottomNavItem(
        route = Routes.Profile.route,
        label = "Profile",
        icon = Icons.Outlined.PersonOutline
    )

    companion object {
        val items = listOf(
            Home,
            Category,
            Cart,
            Profile
        )
    }
}