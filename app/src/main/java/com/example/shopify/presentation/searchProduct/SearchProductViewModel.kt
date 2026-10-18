package com.example.shopify.presentation.searchProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.core.common.Resource
import com.example.shopify.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SearchProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val uiState: StateFlow<SearchProductsUiState> =
        _query.debounce(300.milliseconds)
            .distinctUntilChanged()
            .flatMapLatest { query ->

                if (query.isBlank()) {
                    flowOf(SearchProductsUiState.Idle)
                } else {
                    productRepository.searchProducts(query)
                        .map { result ->
                            when (result) {

                                Resource.Loading ->
                                    SearchProductsUiState.Loading

                                is Resource.Success ->
                                    SearchProductsUiState.Success(
                                        result.data
                                    )

                                is Resource.Error ->
                                    SearchProductsUiState.Error(
                                        result.message
                                    )
                            }
                        }
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                SearchProductsUiState.Idle
            )

    fun search(query: String) {
        _query.value = query
    }
}