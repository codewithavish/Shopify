package com.example.shopify.presentation.cart

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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.shopify.core.ui.theme.BorderColor
import com.example.shopify.core.ui.theme.DeleteRed
import com.example.shopify.core.ui.theme.LightPurple
import com.example.shopify.core.ui.theme.Purple
import com.example.shopify.core.ui.theme.TextDark
import com.example.shopify.core.ui.theme.TextGray
import com.example.shopify.domain.model.CartItem

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onCheckoutClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when (val state = uiState) {

            CartUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Purple
                    )
                }
            }

            is CartUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message,
                        color = TextDark,
                        fontSize = 15.sp
                    )
                }
            }

            is CartUiState.Success -> {
                val cart = state.cart

                val itemCount = cart.items.sumOf {
                    it.quantity
                }

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 8.dp,
                                end = 20.dp,
                                top = 16.dp,
                                bottom = 12.dp
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
                            text = "Cart",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )

                        Spacer(
                            modifier = Modifier.width(6.dp)
                        )

                        Text(
                            text = "($itemCount items)",
                            fontSize = 13.sp,
                            color = TextGray
                        )
                    }

                    if (cart.items.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(RoundedCornerShape(45.dp))
                                    .background(LightPurple),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ShoppingBag,
                                    contentDescription = null,
                                    tint = Purple,
                                    modifier = Modifier.size(42.dp)
                                )
                            }

                            Spacer(
                                modifier = Modifier.height(20.dp)
                            )

                            Text(
                                text = "Your cart is empty",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = "Looks like you haven't added anything yet.",
                                fontSize = 14.sp,
                                color = TextGray
                            )
                        }

                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            item {
                                Spacer(
                                    modifier = Modifier.height(2.dp)
                                )
                            }

                            items(
                                items = cart.items,
                                key = { it.itemId }
                            ) { item ->

                                CartItemRow(
                                    item = item,
                                    onIncrease = {
                                        viewModel.increaseQuantity(
                                            item.itemId
                                        )
                                    },
                                    onDecrease = {
                                        viewModel.decreaseQuantity(
                                            item.itemId
                                        )
                                    },
                                    onDelete = {
                                        viewModel.removeItem(
                                            item.itemId
                                        )
                                    }
                                )
                            }

                            item {

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp)
                                ) {

                                    Text(
                                        text = "Price Details",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark
                                    )

                                    Spacer(
                                        modifier = Modifier.height(14.dp)
                                    )

                                    PriceRow(
                                        label = "Subtotal",
                                        value = formatPrice(
                                            cart.subtotal
                                        )
                                    )

                                    Spacer(
                                        modifier = Modifier.height(8.dp)
                                    )

                                    PriceRow(
                                        label = "Delivery",
                                        value = formatPrice(
                                            cart.deliveryFee
                                        )
                                    )

                                    Spacer(
                                        modifier = Modifier.height(8.dp)
                                    )

                                    PriceRow(
                                        label = "Discount",
                                        value = "-${formatPrice(cart.discount)}",
                                        valueColor = Color(0xFFE83D58)
                                    )

                                    Spacer(
                                        modifier = Modifier.height(14.dp)
                                    )

                                    HorizontalDivider(
                                        color = BorderColor
                                    )

                                    Spacer(
                                        modifier = Modifier.height(14.dp)
                                    )

                                    PriceRow(
                                        label = "Total",
                                        value = formatPrice(
                                            cart.total
                                        ),
                                        labelFontWeight = FontWeight.Bold,
                                        valueFontWeight = FontWeight.Bold,
                                        valueColor = Purple
                                    )

                                    Spacer(
                                        modifier = Modifier.height(20.dp)
                                    )

                                    Button(
                                        onClick = onCheckoutClick,
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
                                            text = "Checkout",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }

                                    Spacer(
                                        modifier = Modifier.height(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = item.thumbnail,
            contentDescription = item.title,
            modifier = Modifier
                .size(82.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF6F6F8)),
            contentScale = ContentScale.Crop
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = item.title,
                maxLines = 1,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDark
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = formatPrice(item.unitPrice),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Purple
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(7.dp))
                        .background(Color(0xFFF5F4F8))
                        .clickable {
                            onDecrease()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Remove,
                        contentDescription = "Decrease quantity",
                        tint = TextDark,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = item.quantity.toString(),
                    modifier = Modifier.width(32.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(7.dp))
                        .background(Color(0xFFF5F4F8))
                        .clickable {
                            onIncrease()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Increase quantity",
                        tint = TextDark,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        IconButton(
            onClick = onDelete
        ) {
            Icon(
                imageVector = Icons.Outlined.DeleteOutline,
                contentDescription = "Remove item",
                tint = DeleteRed,
                modifier = Modifier.size(21.dp)
            )
        }
    }
}

@Composable
private fun PriceRow(
    label: String,
    value: String,
    labelFontWeight: FontWeight = FontWeight.Normal,
    valueFontWeight: FontWeight = FontWeight.Normal,
    valueColor: Color = TextDark
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = labelFontWeight,
            color = TextGray
        )

        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = valueFontWeight,
            color = valueColor
        )
    }
}

private fun formatPrice(
    price: Double
): String {
    return "$%.2f".format(price)
}