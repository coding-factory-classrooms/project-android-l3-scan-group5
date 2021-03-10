package com.codingfactoryprojet.scanneropenfoodfact.entity.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.kittinunf.fuel.Fuel
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.*

const val apiURL = " https://world.openfoodfacts.org/api/v0/product/"

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "barcode") val barcode: Long,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "image_url") val imageURL: String?,
    @ColumnInfo(name = "labels") val labels: String?,
    @ColumnInfo(name = "ingredients") val ingredients: String ?,
    @ColumnInfo(name = "categories") val categories: String?,
    @ColumnInfo(name = "brand") val brand: String?,
    @ColumnInfo(name = "created_at") val createdAt: LocalDateTime
)

fun getProductData(barcode: Long) {
   Fuel.get("$apiURL$barcode.json").response { _, _, result ->
       val (bytes, error) = result
       if(bytes != null) {
           val data = JSONObject(String(bytes)).getJSONObject("product")
           val productName = data.getString("product_name")
           val imageURl = data.getString("image_url")
           val labels = data.getString("labels")
           val ingredientsText = data.getString("ingredients_text")
           val categories = data.getString("categories")
           val brand = data.getString("brand_owner_imported")
           Product(
               barcode = barcode,
               name = productName,
               imageURL = imageURl,
               labels = labels,
               ingredients = ingredientsText,
               categories = categories,
               brand = brand,
               createdAt = LocalDateTime.now()
           )
       }
   }
}