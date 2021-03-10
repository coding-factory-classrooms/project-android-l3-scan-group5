package com.codingfactoryprojet.scanneropenfoodfact.product

import android.util.JsonReader
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import org.json.JSONObject

const val apiURL = " https://world.openfoodfacts.org/api/v0/product/"
data class Product(
    val barcode: Long,
    var productName: String = "Unknown", // product_name
    var imageURL: String = "Unknown", // image_url
    var labels: String = "Unknown", // labels
    var ingredientsText: String = "Unknown", // ingredients_text
    var categories: String = "Unknown", // categories
    var brand: String = "Unknown" // brand_owner_imported
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
           Product(barcode = barcode,
                    productName = productName,
                    imageURL = imageURl,
                    labels = labels,
                    ingredientsText = ingredientsText,
                    categories = categories,
                    brand = brand)
       }
   }
}