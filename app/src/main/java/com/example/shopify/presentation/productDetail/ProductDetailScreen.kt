package com.example.shopify.presentation.productDetail

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.shopify.core.ui.theme.Purple
import com.example.shopify.core.ui.theme.TextDark
import com.example.shopify.core.ui.theme.TextGray
import com.example.shopify.domain.model.Product
import com.example.shopify.presentation.cart.CartViewModel

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBackClick: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val cartViewModel: CartViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(productId) {
        viewModel.getProduct(productId)
    }

    when (val state = uiState) {
        ProductDetailUiState.Loading -> {
            LoadingContent()
        }

        is ProductDetailUiState.Error -> {
            ErrorContent(
                message = state.message
            )
        }

        is ProductDetailUiState.Success -> {
            ProductDetailContent(
                product = state.product,
                onBackClick = onBackClick,
                onAddToCart = {
                    cartViewModel.addToCart(state.product)
                }
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Purple
        )
    }
}

@Composable
private fun ErrorContent(
    message: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = TextDark,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun ProductDetailContent(
    product: Product,
    onBackClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    var selectedImage by remember {
        mutableIntStateOf(0)
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
                    end = 12.dp,
                    top = 8.dp,
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

            Spacer(
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Wishlist",
                    tint = TextDark
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ProductMainImage(
                product = product,
                selectedImage = selectedImage
            )
            if (product.images.size > 1) {
                ImageDots(
                    imageCount = product.images.size,
                    selectedImage = selectedImage
                )
            }
            if (product.images.size > 1) {
                ImageThumbnails(
                    images = product.images,
                    selectedImage = selectedImage,
                    onImageSelected = {
                        selectedImage = it
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                product.category?.let { category ->
                    Text(
                        text = category.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Purple
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )
                }

                Text(
                    text = product.title,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = formatPrice(product.price),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Purple
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                HorizontalDivider(
                    color = Color(0xFFE8E8EC)
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                Text(
                    text = "Description",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    color = TextGray
                )

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                Button(
                    onClick = onAddToCart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple
                    )
                ) {

                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "Add to Cart",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }
        }
    }
}

@Composable
private fun ProductMainImage(
    product: Product,
    selectedImage: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFFF7F7FA)),
        contentAlignment = Alignment.Center
    ) {

        val imageUrl =
            product.images.getOrNull(selectedImage)
                ?: product.thumbnail

        AsyncImage(
            model = imageUrl,
            contentDescription = product.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun ImageDots(
    imageCount: Int,
    selectedImage: Int
) {
    Spacer(
        modifier = Modifier.height(12.dp)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        repeat(imageCount) { index ->

            Box(
                modifier = Modifier
                    .padding(horizontal = 3.dp)
                    .size(
                        if (index == selectedImage) {
                            8.dp
                        } else {
                            6.dp
                        }
                    )
                    .clip(CircleShape)
                    .background(
                        if (index == selectedImage) {
                            Purple
                        } else {
                            Color(0xFFD4D4DC)
                        }
                    )
            )
        }
    }
}

@Composable
private fun ImageThumbnails(
    images: List<String>,
    selectedImage: Int,
    onImageSelected: (Int) -> Unit
) {
    Spacer(
        modifier = Modifier.height(14.dp)
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        itemsIndexed(images) { index, image ->

            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        start = if (index == 0) 16.dp else 0.dp,
                        end = if (index == images.lastIndex) {
                            16.dp
                        } else {
                            0.dp
                        }
                    )
                    .size(64.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF6F6F8))
                    .clickable {
                        onImageSelected(index)
                    },
                contentScale = ContentScale.Crop
            )
        }
    }
}

private fun formatPrice(
    price: Double
): String {
    return "$%.2f".format(price)
}