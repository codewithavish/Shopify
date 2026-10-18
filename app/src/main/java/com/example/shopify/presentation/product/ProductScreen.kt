package com.example.shopify.presentation.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.shopify.core.ui.theme.BorderColor
import com.example.shopify.core.ui.theme.CardBackground
import com.example.shopify.core.ui.theme.Purple
import com.example.shopify.core.ui.theme.StarColor
import com.example.shopify.core.ui.theme.TextDark
import com.example.shopify.core.ui.theme.TextGray
import com.example.shopify.domain.model.Product
import com.example.shopify.presentation.searchProduct.SearchProductsUiState
import com.example.shopify.presentation.searchProduct.SearchProductsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce



@Composable
fun ProductScreen(
    categoryId: Int?,
    categoryName: String = "Products",
    onProductClick: (Int) -> Unit,
    onBackClick: () -> Unit = {},
    productViewModel: ProductViewModel = hiltViewModel(),
    searchViewModel: SearchProductsViewModel = hiltViewModel()
){

    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    val productUiState by productViewModel.uiState.collectAsStateWithLifecycle()

    val searchUiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    val sortOption by productViewModel.sortOption.collectAsStateWithLifecycle()

    LaunchedEffect(categoryId) {
        if (categoryId == null) {
            productViewModel.getProducts()
        } else {
            productViewModel.getProductsByCategory(categoryId)
        }
    }


    LaunchedEffect(Unit) {
        snapshotFlow {
            searchText
        }
            .debounce(500)
            .collectLatest { query ->
                if (query.length >= 2) {
                    searchViewModel.search(query)
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 4.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = TextDark
                )
            }

            Text(
                text = categoryName,
                modifier = Modifier.weight(1f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }

        ProductSearchField(
            value = searchText,
            onValueChange = {
                searchText = it
            }
        )

        ProductContent(
            searchText = searchText,
            productUiState = productUiState,
            searchUiState = searchUiState,
            onProductClick = onProductClick,
            sortOption = sortOption,
            onSortChange = productViewModel::onSortOptionChange,
            sortProducts = productViewModel::getFilterProduct
        )

    }
}


@Composable
private fun ProductSearchField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .height(52.dp),
        singleLine = true,
        placeholder = {
            Text(
                text = "Search products...",
                color = TextGray,
                fontSize = 13.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = TextGray,
                modifier = Modifier.size(20.dp)
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BorderColor,
            unfocusedBorderColor = BorderColor,
            focusedContainerColor = Color(0xFFF8F8FA),
            unfocusedContainerColor = Color(0xFFF8F8FA),
            cursorColor = Purple
        )
    )
}


@Composable
private fun ProductContent(
    searchText: String,
    productUiState: ProductUiState,
    searchUiState: SearchProductsUiState,
    onProductClick: (Int) -> Unit,
    sortOption: ProductSortOption,
    onSortChange: (ProductSortOption) -> Unit,
    sortProducts: (List<Product>, ProductSortOption) -> List<Product>
) {
    if (searchText.length >= 2) {
        SearchProductContent(
            uiState = searchUiState,
            onProductClick = onProductClick,
            sortOption = sortOption,
            onSortChange = onSortChange,
            sortProducts = sortProducts
        )

    } else {
        CategoryProductContent(
            uiState = productUiState,
            onProductClick = onProductClick,
            sortOption = sortOption,
            onSortChange = onSortChange,
            sortProducts = sortProducts
        )
    }
}


@Composable
private fun CategoryProductContent(
    uiState: ProductUiState,
    onProductClick: (Int) -> Unit,
    sortOption: ProductSortOption,
    onSortChange: (ProductSortOption) -> Unit,
    sortProducts: (List<Product>, ProductSortOption) -> List<Product>
) {

    when (uiState) {
        ProductUiState.Loading -> {
            ProductLoading()
        }

        is ProductUiState.Success -> {
            ProductGrid(
                products = uiState.product,
                onProductClick = onProductClick,
                sortOption = sortOption,
                onSortChange = onSortChange,
                sortProducts = sortProducts
            )
        }

        is ProductUiState.Error -> {
            ProductError(
                message = uiState.message
            )
        }
    }
}

@Composable
private fun SearchProductContent(
    uiState: SearchProductsUiState,
    onProductClick: (Int) -> Unit,
    sortOption: ProductSortOption,
    onSortChange: (ProductSortOption) -> Unit,
    sortProducts: (List<Product>, ProductSortOption) -> List<Product>
) {

    when (uiState) {
        SearchProductsUiState.Idle -> {
            ProductLoading()
        }

        SearchProductsUiState.Loading -> {
            ProductLoading()
        }

        is SearchProductsUiState.Success -> {
            ProductGrid(
                products = uiState.products,
                onProductClick = onProductClick,
                sortOption = sortOption,
                onSortChange = onSortChange,
                sortProducts = sortProducts
            )
        }

        is SearchProductsUiState.Error -> {
            ProductError(
                message = uiState.message
            )
        }
    }
}



@Composable
private fun ProductGrid(
    products: List<Product>,
    onProductClick: (Int) -> Unit,
    sortOption: ProductSortOption,
    onSortChange: (ProductSortOption) -> Unit,
    sortProducts: (List<Product>, ProductSortOption) -> List<Product>
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    val sortedProducts = sortProducts(products, sortOption)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            start = 12.dp,
            end = 12.dp,
            top = 4.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            // Box anchors the dropdown to the sort row.
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .clickable {
                            expanded = true // FIX: open dropdown
                        }
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FilterList,
                        contentDescription = "Sort products",
                        tint = TextDark
                    )

                    Spacer(Modifier.size(8.dp))

                    Text(
                        text = sortOption.label(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDark
                    )

                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "Open sort options",
                        tint = TextDark,
                        modifier = Modifier.size(18.dp)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    ProductSortOption.values().forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(option.label())
                            },
                            onClick = {
                                onSortChange(option)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        items(
            items = sortedProducts,
            key = { it.id }
        ) { product ->
            ProductItem(
                product = product,
                onClick = { onProductClick(product.id) }
            )
        }
    }
}


// NEW: Friendly labels for the dropdown.
private fun ProductSortOption.label(): String {
    return when (this) {
        ProductSortOption.DEFAULT -> "Default"
        ProductSortOption.PRICE_LOW_TO_HIGH -> "Price: Low to High"
        ProductSortOption.PRICE_HIGH_TO_LOW -> "Price: High to Low"
        ProductSortOption.NAME_A_TO_Z -> "Name: A to Z"
        ProductSortOption.NAME_Z_TO_A -> "Name: Z to A"
    }
}


@Composable
private fun ProductItem(
    product: Product,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.88f)
                .clip(
                    RoundedCornerShape(14.dp)
                )
                .background(CardBackground)
        ) {

            AsyncImage(
                model = product.images.firstOrNull(),
                contentDescription = product.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .padding(7.dp)
                    .size(30.dp)
                    .align(Alignment.TopEnd)
                    .clip(
                        RoundedCornerShape(15.dp)
                    )
                    .background(Color.White.copy(alpha = 0.92f)),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Wishlist",
                    tint = TextDark,
                    modifier = Modifier.size(17.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        Text(
            text = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = formatPrice(product.price),
                modifier = Modifier.weight(1f),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Purple
            )

            Text(
                text = "★ 4.5",
                fontSize = 10.sp,
                color = StarColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Composable
private fun ProductLoading() {

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            color = Purple
        )
    }
}

@Composable
private fun ProductError(
    message: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = message,
            fontSize = 14.sp,
            color = TextGray
        )
    }
}

private fun formatPrice(
    price: Double
): String {
    return "$%.2f".format(price)
}