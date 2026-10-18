package com.example.shopify.presentation.searchProduct

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.shopify.core.ui.theme.BackgroundColor
import com.example.shopify.core.ui.theme.LightPurple
import com.example.shopify.core.ui.theme.PrimaryPurple
import com.example.shopify.core.ui.theme.TextPrimary
import com.example.shopify.core.ui.theme.TextSecondary
import com.example.shopify.domain.model.Product

@Composable
fun SearchProductsScreen(
    onBackClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    viewModel: SearchProductsViewModel = hiltViewModel()
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val focusRequester = remember {
        FocusRequester()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back"
                )
            }

            OutlinedTextField(
                value = query,
                onValueChange = viewModel::search,
                modifier = Modifier.weight(1f),
                singleLine = true,
                placeholder = {
                    Text("Search products...")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null
                    )
                },
                shape = RoundedCornerShape(14.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        when (val state = uiState) {

            SearchProductsUiState.Idle -> {
                Text(
                    text = "Search for products",
                    color = TextSecondary
                )
            }

            SearchProductsUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = PrimaryPurple
                    )
                }
            }

            is SearchProductsUiState.Success -> {

                if (state.products.isEmpty()) {
                    Text(
                        text = "No products found",
                        color = TextSecondary
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = state.products,
                            key = { it.id }
                        ) { product ->

                            ProductSearchItem(
                                product = product,
                                onClick = {
                                    onProductClick(product.id)
                                }
                            )
                        }
                    }
                }
            }

            is SearchProductsUiState.Error -> {
                Text(
                    text = state.message,
                    color = Color.Red
                )
            }
        }
    }
}

@Composable
private fun ProductSearchItem(
    product: Product,
    onClick: () -> Unit
) {
    var imageFailed by remember(product.id) {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(LightPurple),
            contentAlignment = Alignment.Center
        ) {

            if (
                product.thumbnail.isNotBlank() &&
                !imageFailed
            )
                AsyncImage(
                    model = product.thumbnail,
                    contentDescription = product.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    onError = {
                        imageFailed = true
                    }
                ) else
                Icon(
                    imageVector = Icons.Outlined.ShoppingBag,
                    contentDescription = product.title,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(36.dp)
                )
        }

        Spacer(
            modifier = Modifier.width(12.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            product.category?.let { category ->
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )
            }

            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PrimaryPurple
            )
        }
    }
}