package com.codingfactoryprojet.scanneropenfoodfact.productlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingfactoryprojet.scanneropenfoodfact.R
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ItemProductBinding
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product
import com.squareup.picasso.Picasso

class ProductListAdapter(private var products: List<Product>) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>(){
    class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        Log.i("ProductListAdapter", product.imageURL)
        with (holder.binding){
            nameTextView.text = product.name
            nutritionImageView.setImageResource(R.drawable.nutrition_grade_b)
            Picasso.get().load(product.imageURL).into(productImageView)
        }

    }

    override fun getItemCount(): Int = products.size

    fun updateDataSet(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

}