package com.codingfactoryprojet.scanneropenfoodfact.productlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ActivityProductListBinding
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product
import com.codingfactoryprojet.scanneropenfoodfact.scanner.BarCodeScannerActivity
import com.github.kittinunf.fuel.Fuel
import org.json.JSONObject

const val TAG = "ProductListActivity"
const val apiURL = " https://world.openfoodfacts.org/api/v0/product/"

class ProductListActivity : AppCompatActivity() {

    private val model: ProductListViewModel by viewModels {
        ProductListViewModelFactory((application as ProductApplication).repository
        )
    }

    private lateinit var binding: ActivityProductListBinding
    private lateinit var adapter: ProductListAdapter
    private val newScannedProductRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //submit Live Data to get Products
        model.allProducts.observe(this, Observer { products -> updateProducts(products!!)})


        adapter = ProductListAdapter(listOf())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.scanProductButton.setOnClickListener {
            val intent = Intent(this, BarCodeScannerActivity::class.java)
            startActivityForResult(intent, newScannedProductRequestCode)
        }

        model.allProducts
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == newScannedProductRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra("barcode")?.let { value ->
                val barcode = value.toLong()
                getDataForProduct(barcode = barcode)
            }
        }
    }

    private fun updateProducts(products: List<Product>) {
        adapter.updateDataSet(products)
    }

    private fun getDataForProduct(barcode: Long) {
        Fuel.get("$apiURL$barcode.json").response { _, _, result ->
            val (bytes, error) = result
            if(bytes != null) {
                val status = JSONObject(String(bytes)).getInt("status")
                if(status == 1) {
                    val data = JSONObject(String(bytes))?.getJSONObject("product")
                    val productName = data.getString("product_name")
                    val imageURl = data.getString("image_url")
                    val labels = data.getString("labels")
                    val categories = data.getString("categories")
                    val nutriscore = data.getString("nutriscore_grade")
                    val caloriesPer100g = data.getJSONObject("nutriments").getInt("energy-kcal")
                    val product = Product(
                        barcode = barcode,
                        name = productName,
                        imageURL = imageURl,
                        labels = labels,
                        categories = categories,
                        caloriesPer100g = caloriesPer100g,
                        nutriscore = nutriscore
                    )
                    model.insert(product)
                }
                else {
                    Toast.makeText(applicationContext,
                        "Product unknown to database",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else {
                Toast.makeText(applicationContext,
                    "An error occurred during the treatment of your request",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}