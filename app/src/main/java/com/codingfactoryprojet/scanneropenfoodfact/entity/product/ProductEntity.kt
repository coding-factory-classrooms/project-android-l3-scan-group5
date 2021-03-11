package com.codingfactoryprojet.scanneropenfoodfact.entity.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity
data class Product(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "barcode") val barcode: Long,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "image_url") val imageURL: String?,
    @ColumnInfo(name = "labels") val labels: String?,
    @ColumnInfo(name = "categories") val categories: String?,
    @ColumnInfo(name = "nutriscore") val nutriscore: String,
    @ColumnInfo(name = "calories") val caloriesPer100g: Int,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean? = false,
    @ColumnInfo(name = "created_at") val createdAt: String? = LocalDate.now().toString()
)