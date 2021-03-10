package com.codingfactoryprojet.scanneropenfoodfact.productlist

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ItemProductBinding

class ProductAdapter(private var products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){
    class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        with (holder.binding){
        nameTextView.text = product.name
        nutritionImageView.setImageResource(product.nutritionImageId)
        productImageView.setImageResource(product.imageId)
        }

    }

    fun updateDataSet(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

}