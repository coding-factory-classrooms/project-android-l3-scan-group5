package com.codingfactoryprojet.scanneropenfoodfact.productlist

import android.app.Application
import com.codingfactoryprojet.scanneropenfoodfact.database.ProductRoomDatabase
import com.codingfactoryprojet.scanneropenfoodfact.service.product.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ProductApplication: Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { ProductRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy  { ProductRepository(database.productDao()) }
}