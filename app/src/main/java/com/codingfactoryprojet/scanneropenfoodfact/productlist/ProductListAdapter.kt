package com.codingfactoryprojet.scanneropenfoodfact.productlist


import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.codingfactoryprojet.scanneropenfoodfact.ProductDetail
import com.codingfactoryprojet.scanneropenfoodfact.R
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ItemProductBinding
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product
import com.squareup.picasso.Picasso

class ProductListAdapter(private var products: List<Product>) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>(){

    class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        context = parent.context
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
        holder.itemView.setOnClickListener{
            val intent = Intent(context,ProductDetail::class.java).apply {
                putExtra("name",product.name)
                putExtra("calories100", product.caloriesPer100g)
                putExtra("labels", product.labels)
                putExtra("categories",product.categories)
                putExtra("nutriscore",product.nutriscore)
                putExtra("urlImage", product.imageURL)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = products.size

    fun updateDataSet(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }


}

