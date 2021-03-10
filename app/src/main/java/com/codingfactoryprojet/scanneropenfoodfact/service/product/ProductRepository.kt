package com.codingfactoryprojet.scanneropenfoodfact.service.product

import androidx.annotation.WorkerThread
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: List<Product> = productDao.getAll()

    fun getProductByBarcode(barcode: Long) {
        productDao.getProductByBarcode(barcode)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    fun delete(product: Product){
        productDao.delete(product)
    }

}