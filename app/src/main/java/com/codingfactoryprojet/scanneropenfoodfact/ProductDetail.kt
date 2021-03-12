package com.codingfactoryprojet.scanneropenfoodfact

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ActivityProductDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.*

class ProductDetail : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  =  ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val labels = intent.getStringExtra("labels")
        val category = intent.getStringExtra("categories")
        val calories = intent.getIntExtra("calories100", 0)
        val nutriscore = intent.getStringExtra("nutriscore")
        val urlImage = intent.getStringExtra("urlImage")

        binding.valueCategorie.text = category
        binding.valueLabel.text = labels
        binding.valueCalories.text = calories.toString()
        binding.valueName.text = name
        binding.valueNutriscore.text = nutriscore.toUpperCase()
        Picasso.get().load(urlImage).into(binding.ProductimageView)
    }
}