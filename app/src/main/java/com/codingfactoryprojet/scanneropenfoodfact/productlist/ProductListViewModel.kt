package com.codingfactoryprojet.scanneropenfoodfact.productlist

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product
import com.codingfactoryprojet.scanneropenfoodfact.service.product.ProductRepository
import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.launch
import org.json.JSONObject
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

    fun getDataAndInsertToDatabase(barcode: Long, applicationContext: Context) =
        run {
            Fuel.get("$apiURL$barcode.json").response { _, _, result ->
                val (bytes, error) = result
                if(bytes != null) {
                    val status = JSONObject(String(bytes)).getInt("status")
                    if(status == 1) {
                        val data = JSONObject(String(bytes))?.getJSONObject("product")
                        val productName = if(data.has("product_name")) data.getString("product_name") else  "Unknown";
                        val imageURl = if(data.has("image_url")) data.getString("image_url") else "https://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/article_thumbnails/reference_guide/cats_and_excessive_meowing_ref_guide/1800x1200_cats_and_excessive_meowing_ref_guide.jpg"
                        val labels = if(data.has("labels")) data.getString("labels") else "Unknown"
                        val categories = if(data.has("categories")) data.getString("categories") else "Unknown"
                        val nutriscore = if(data.has("nutriscore_grade")) data.getString("nutriscore_grade") else "Unknown"
                        val caloriesPer100g = if(data.has("nutriments"))  if((data.getJSONObject("nutriments").has("energy-kcal"))) data.getJSONObject("nutriments").getInt("energy-kcal") else 0 else 0
                        val product = Product(
                            barcode = barcode,
                            name = productName,
                            imageURL = imageURl,
                            labels = labels,
                            categories = categories,
                            caloriesPer100g = caloriesPer100g as Int,
                            nutriscore = nutriscore
                        )
                        viewModelScope.launch {
                            repository.insert(product)
                        }
                    } else {
                        Toast.makeText(applicationContext,
                            "Product unknown to database",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(applicationContext,
                        "An error occurred during the treatment of your request",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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