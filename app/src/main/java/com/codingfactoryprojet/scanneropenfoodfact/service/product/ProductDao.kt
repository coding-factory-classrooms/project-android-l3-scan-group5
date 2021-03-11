package com.codingfactoryprojet.scanneropenfoodfact.service.product

import androidx.room.*
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT * FROM product WHERE barcode = :barcode")
    fun getProductByBarcode(barcode: Long): Product

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg product: Product)

    @Delete
    suspend fun delete(product: Product)
}