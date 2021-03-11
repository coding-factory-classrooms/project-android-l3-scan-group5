package com.codingfactoryprojet.scanneropenfoodfact.productlist

import androidx.lifecycle.*
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product
import com.codingfactoryprojet.scanneropenfoodfact.service.product.ProductRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class ProductListViewModel(private val repository: ProductRepository) : ViewModel() {
    // Using LiveData and caching what allProducts returns has several benefits:
    // - We can put an observer on the data and only update the
    //   the UI when the data actually changes. (equivalent of useEffect in react native)
    // - Repository is completely separated from the UI through the ViewModel.
    val allProducts: LiveData<List<Product>> = repository.allProducts.asLiveData()

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }
}

class ProductListViewModelFactory(private val repository: ProductRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}