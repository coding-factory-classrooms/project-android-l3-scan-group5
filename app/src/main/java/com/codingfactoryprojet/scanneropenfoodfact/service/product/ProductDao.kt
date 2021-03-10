package com.codingfactoryprojet.scanneropenfoodfact.service.product

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM product WHERE barcode = :barcode")
    fun getProductByBarcode(barcode: Long): Product

    @Insert
    fun insert(vararg product: Product)

    @Delete
    fun delete(product: Product)
}