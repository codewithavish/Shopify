package com.example.shopify.presentation.navigation

sealed class Routes(
    val route: String
) {

    data object Login : Routes(
        route = "login"
    )

    data object Register : Routes(
        route = "register"
    )

    data object Main : Routes(
        route = "main"
    )


    data object Home : Routes(
        route = "home"
    )

    data object Category : Routes(
        route = "category"
    )

    data object Cart : Routes(
        route = "cart"
    )

    data object Profile : Routes(
        route = "profile"
    )


    data object SearchProducts : Routes(
        route = "search"
    )

    data object ProductList : Routes(
        route = "products?categoryId={categoryId}"
    ) {
        fun createRoute(
            categoryId: Int? = null
        ): String {
            return if (categoryId != null) {
                "products?categoryId=$categoryId"
            } else {
                "products"
            }
        }
    }


    data object ProductDetail : Routes(
        route = "product/{productId}"
    ) {
        fun createRoute(
            productId: Int
        ): String {
            return "product/$productId"
        }
    }

    data object EditProfile : Routes(
        route = "profile/edit"
    )

    data object Camera : Routes(
        route = "camera"
    )
}