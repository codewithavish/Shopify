package com.example.shopify.presentation.navigation

import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.shopify.core.ui.theme.PrimaryPurple
import com.example.shopify.core.ui.theme.UnselectedColor

@Composable
fun MainBottomNavigationBar(
    currentRoute: String?,
    cartItemCount: Int,
    onItemSelected: (MainBottomNavItem) -> Unit
) {
    NavigationBar(
        containerColor = Color.White
    ) {

        MainBottomNavItem.items.forEach { item ->

            NavigationBarItem(
                selected = currentRoute == item.route,

                onClick = {
                    onItemSelected(item)
                },

                icon = {

                    if (
                        item == MainBottomNavItem.Cart &&
                        cartItemCount > 0
                    ) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = Color.Red
                                ) {
                                    Text(
                                        text = cartItemCount.toString()
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label
                        )
                    }
                },

                label = {
                    Text(item.label)
                },

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryPurple,
                    selectedTextColor = PrimaryPurple,
                    unselectedIconColor = UnselectedColor,
                    unselectedTextColor = UnselectedColor,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}