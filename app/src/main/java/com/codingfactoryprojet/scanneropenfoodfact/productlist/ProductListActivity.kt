package com.codingfactoryprojet.scanneropenfoodfact.productlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ActivityProductListBinding
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product

class ProductListActivity : AppCompatActivity() {

    private val model: ProductListViewModel by viewModels {
        ProductListViewModelFactory((application as ProductApplication).repository
        )
    }

    private lateinit var binding: ActivityProductListBinding
    private lateinit var adapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //submit Live Data to get Products
        model.allProducts.observe(this, Observer { products -> updateProducts(products!!)})


        adapter = ProductListAdapter(listOf())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        model.allProducts
    }

    private fun updateProducts(products: List<Product>) {
        adapter.updateDataSet(products)
    }
}