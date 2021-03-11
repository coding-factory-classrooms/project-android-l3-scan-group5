package com.codingfactoryprojet.scanneropenfoodfact.productlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingfactoryprojet.scanneropenfoodfact.R
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {

    private val model: ProductListViewModel by viewModels()

    private lateinit var binding: ActivityProductListBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //submit Live Data to get Products
        model.getProductsLiveData().observe(this, Observer { products -> updateProducts(products!!)})


        adapter = ProductAdapter(listOf())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        //call the ViewModel.loadProducts function
        model.loadProducts()
    }

    private fun updateProducts(products: List<Product>) {
        adapter.updateDataSet(products)
    }
}