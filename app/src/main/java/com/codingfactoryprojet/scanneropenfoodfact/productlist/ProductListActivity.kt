package com.codingfactoryprojet.scanneropenfoodfact.productlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingfactoryprojet.scanneropenfoodfact.MainActivity
import com.codingfactoryprojet.scanneropenfoodfact.R
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ActivityProductListBinding
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product
import com.codingfactoryprojet.scanneropenfoodfact.scanner.BarCodeScannerActivity
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_product_list.*
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

        binding.bottomNavigation.selectedItemId = R.id.Product

        binding.scanProductButton.setOnClickListener {
            val intent = Intent(this, BarCodeScannerActivity::class.java)
            startActivityForResult(intent, newScannedProductRequestCode)
        }

        bottom_navigation.setOnNavigationItemSelectedListener(bottomNavigationListener)

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

    private val bottomNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
        Log.i("MainActivity", "ok: ${item.itemId} ")
        when (item.itemId) {
            R.id.Home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
                true
            }
            R.id.Product -> {
                val intent = Intent(this, ProductListActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
                true
            }
        }
        false
    }
}