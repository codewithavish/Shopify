package com.example.shopify.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.core.common.Resource
import com.example.shopify.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    val repository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()


    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            repository.getCategoryList().collect { result ->
                when(result){
                    Resource.Loading -> {
                        _uiState.value = CategoryUiState.Loading
                    }
                    is Resource.Success -> {
                        _uiState.value = CategoryUiState.Success(
                            result.data
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = CategoryUiState.Error(
                            result.message
                        )

                    }

                }

            }
        }

    }
}