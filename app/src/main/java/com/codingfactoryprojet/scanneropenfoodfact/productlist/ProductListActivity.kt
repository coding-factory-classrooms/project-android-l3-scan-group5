package com.codingfactoryprojet.scanneropenfoodfact.productlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingfactoryprojet.scanneropenfoodfact.MainActivity
import com.codingfactoryprojet.scanneropenfoodfact.R
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ActivityProductListBinding
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product
import com.codingfactoryprojet.scanneropenfoodfact.scanner.BarCodeScannerActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_product_list.*

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
                model.getDataAndInsertToDatabase(barcode = barcode, applicationContext = this.applicationContext)
            }
        }
    }

    private fun updateProducts(products: List<Product>) {
        adapter.updateDataSet(products)
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