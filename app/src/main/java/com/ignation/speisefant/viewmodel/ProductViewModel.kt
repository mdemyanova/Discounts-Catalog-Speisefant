package com.ignation.speisefant.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ignation.speisefant.database.ProductRoomDatabase
import com.ignation.speisefant.domain.Product
import com.ignation.speisefant.repository.ProductRepository
import kotlinx.coroutines.launch

const val TAG = "ProductViewModel"

class ProductViewModel(application: Application) : ViewModel() {

    private val productRepository = ProductRepository(ProductRoomDatabase.getDatabase(application))
    private val allActualProducts = productRepository.products
    val productsOrderByShop = productRepository.productsOrderByShop

    lateinit var productsByShop: LiveData<List<Product>>

    init {
        refreshDataFromRepository()
        Log.d(TAG, "ViewModel created")
    }

    fun getProductsByType(type: String, productsDataset: LiveData<List<Product>>): LiveData<List<Product>> {
        return Transformations.map(productsDataset) { list ->
            list.filter { it.type == type }
        }
    }

    fun getProductsByShop(shopName: String): LiveData<List<Product>> {
        return Transformations.map(allActualProducts) { list ->
            list.filter { it.shop == shopName }
        }
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                productRepository.refreshProducts()
            } catch (e: Exception) {
                Log.d(TAG, "refreshDataFromRepository: ${e.message}")
            }
        }
    }
}

class ProductViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}