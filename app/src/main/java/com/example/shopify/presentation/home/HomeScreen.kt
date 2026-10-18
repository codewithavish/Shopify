package com.example.shopify.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.shopify.core.ui.theme.BackgroundColor
import com.example.shopify.core.ui.theme.LightPurple
import com.example.shopify.core.ui.theme.PrimaryPurple
import com.example.shopify.core.ui.theme.Purple
import com.example.shopify.core.ui.theme.TextPrimary
import com.example.shopify.core.ui.theme.TextSecondary
import com.example.shopify.domain.model.Category
import com.example.shopify.domain.model.Product
import com.example.shopify.presentation.profile.ProfileUiState
import com.example.shopify.presentation.profile.ProfileViewModel


@Composable
fun HomeScreen(
    onProductClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onSeeAllCategories: () -> Unit,
    onSeeAllProducts: () -> Unit,
    onCategoryClick: (Int) -> Unit,
    onProfileClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val profileState by profileViewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        HomeUiState.Loading -> {
            HomeLoading()
        }

        is HomeUiState.Error -> {
            HomeError(
                message = state.message
            )
        }

        is HomeUiState.Success -> {

            val userName = (profileState as? ProfileUiState.Success)
                ?.user?.name ?: "there"

            HomeContent(
                products = state.products,
                categories = state.categories,
                onProductClick = onProductClick,
                onSearchClick = onSearchClick,
                onSeeAllCategories = onSeeAllCategories,
                onSeeAllProducts = onSeeAllProducts,
                onCategoryClick = onCategoryClick,
                onProfileClick = onProfileClick,
                userName = userName

            )
        }
    }
}

@Composable
private fun HomeContent(
    products: List<Product>,
    categories: List<Category>,
    onProductClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onSeeAllCategories: () -> Unit,
    onSeeAllProducts: () -> Unit,
    onCategoryClick: (Int) -> Unit,
    onProfileClick: () -> Unit,
    userName: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp)
    ) {

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        HomeHeader(
            onProfileClick = onProfileClick
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        GreetingSection(userName)

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        SearchBar(
            onClick = onSearchClick
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        SectionHeader(
            title = "Categories",
            onSeeAllClick = onSeeAllCategories
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        CategoriesRow(
            categories = categories,
            onCategoryClick = onCategoryClick
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        SpecialOfferBanner(
            onClick = onSeeAllProducts
        )

        Spacer(
            modifier = Modifier.height(26.dp)
        )

        SectionHeader(
            title = "Featured Products",
            onSeeAllClick = onSeeAllProducts
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        FeaturedProducts(
            products = products,
            onProductClick = onProductClick
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )
    }
}

@Composable
private fun HomeHeader(
    onProfileClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(LightPurple),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Outlined.ShoppingBag,
                    contentDescription = "Shopify",
                    tint = PrimaryPurple,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Text(
                text = "Shopify",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        IconButton(
            onClick = {}
        ) {

            Icon(
                imageVector = Icons.Outlined.NotificationsNone,
                contentDescription = "Notifications",
                tint = TextPrimary
            )
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(LightPurple)
                .clickable(onClick = onProfileClick),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "JD",
                color = PrimaryPurple,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun GreetingSection(userName: String) {

    Column {

        Text(
            text = "Hello, ${userName.trim().substringBefore(" ")}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "What are you shopping for today?",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )
    }
}

@Composable
private fun SearchBar(
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .clickable {
                onClick()
            }
            .padding(horizontal = 14.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "Search",
            tint = TextSecondary
        )

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Text(
            text = "Search products...",
            color = TextSecondary,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SectionHeader(
    title: String,
    onSeeAllClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )

        TextButton(
            onClick = onSeeAllClick
        ) {

            Text(
                text = "See All",
                color = PrimaryPurple
            )
        }
    }
}

@Composable
private fun CategoriesRow(
    categories: List<Category>,
    onCategoryClick: (Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(
            items = categories,
            key = { it.id }
        ) { category ->

            var imageFailed by remember(category.id) {
                mutableStateOf(false)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(72.dp)
                    .clickable {
                        onCategoryClick(category.id)
                    }
            ) {

                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(LightPurple),
                    contentAlignment = Alignment.Center
                ) {

                    if (
                        !category.image.isNullOrBlank() && !imageFailed
                    ) {
                        AsyncImage(
                            model = category.image,
                            contentDescription = category.name,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                            onError = {
                                imageFailed = true
                            }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingBag,
                            contentDescription = category.name,
                            tint = Purple,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = category.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextPrimary
                )
            }
        }
    }
}

@Composable
private fun SpecialOfferBanner(
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(168.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(PrimaryPurple)
            .clickable {
                onClick()
            }
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "Special Offer",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Up to 50% Off",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "On selected products",
                color = Color.White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Shop Now →",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Outlined.ShoppingBag,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(58.dp)
            )
        }
    }
}

@Composable
private fun FeaturedProducts(
    products: List<Product>,
    onProductClick: (Int) -> Unit
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        items(
            items = products.take(10),
            key = { it.id }
        ) { product ->

            ProductCard(
                product = product,
                onClick = {
                    onProductClick(product.id)
                }
            )
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .width(155.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable {
                onClick()
            }
    ) {

        AsyncImage(
            model = product.thumbnail,
            contentDescription = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )
                ),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            Text(
                text = product.title,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "$${product.price}",
                color = PrimaryPurple,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun HomeLoading() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            color = PrimaryPurple
        )
    }
}

@Composable
private fun HomeError(
    message: String
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Something went wrong",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = message,
                color = TextSecondary
            )
        }
    }
}