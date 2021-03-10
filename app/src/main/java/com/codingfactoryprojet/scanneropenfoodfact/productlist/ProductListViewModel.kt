package com.codingfactoryprojet.scanneropenfoodfact.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codingfactoryprojet.scanneropenfoodfact.R
import kotlinx.android.synthetic.main.item_product.view.*

data class Product (
    val id : Int,
    val name : String,
    val imageId : Int,
    val nutritionImageId : Int
)

//fake data for now (feat05)
var products = listOf(
Product(142, "chocapic", R.drawable.product_chocapic, R.drawable.nutrition_grade_b),
Product(141, "chocapic", R.drawable.product_chocapic, R.drawable.nutrition_grade_b),
Product(140, "chocapic", R.drawable.product_chocapic, R.drawable.nutrition_grade_b),
Product(139, "chocapic", R.drawable.product_chocapic, R.drawable.nutrition_grade_b),
Product(138, "chocapic", R.drawable.product_chocapic, R.drawable.nutrition_grade_b),
Product(137, "chocapic", R.drawable.product_chocapic, R.drawable.nutrition_grade_b),
Product(136, "chocapic", R.drawable.product_chocapic, R.drawable.nutrition_grade_b)
)


class ProductListViewModel : ViewModel() {

    private val productsLiveData = MutableLiveData<List<Product>>()
    fun getProductsLiveData(): LiveData<List<Product>> = productsLiveData

    fun loadProducts() {
        //fake data for now (feat05)
        productsLiveData.value = products
    }

}