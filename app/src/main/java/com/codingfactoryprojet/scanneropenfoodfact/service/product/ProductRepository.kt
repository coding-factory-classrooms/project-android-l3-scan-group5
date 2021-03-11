package com.codingfactoryprojet.scanneropenfoodfact.service.product

import androidx.annotation.WorkerThread
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because we only need access to the DAO
class ProductRepository(private val productDao: ProductDao) {

    val allProducts: Flow<List<Product>> = productDao.getAll()

    fun getProductByBarcode(barcode: Long) {
        productDao.getProductByBarcode(barcode)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    suspend fun delete(product: Product){
        productDao.delete(product)
    }

}