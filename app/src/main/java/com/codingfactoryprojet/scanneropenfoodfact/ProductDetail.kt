package com.codingfactoryprojet.scanneropenfoodfact

import android.graphics.Color
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
        var nutriscore = intent.getStringExtra("nutriscore")
        val urlImage = intent.getStringExtra("urlImage")

        binding.valueCategorie.text = category
        binding.valueLabel.text = labels
        binding.valueCalories.text = calories.toString()
        binding.valueName.text = name

        Picasso.get().load(urlImage).into(binding.ProductimageView)
        binding.valueNutriscore.text = nutriscore.toUpperCase()

        if(nutriscore.toString().equals("e")){
            binding.valueNutriscore.setTextColor(Color.RED)
        }
        else if(nutriscore.toString().equals("a")){
            binding.valueNutriscore.setTextColor(Color.GREEN)
        }
        else if (nutriscore.toString().equals("d")){
            binding.valueNutriscore.setTextColor(Color.MAGENTA)
        }
        else if(nutriscore.toString().equals("b")){
            binding.valueNutriscore.setTextColor(Color.BLUE)

        }
        else if (nutriscore.toString().equals("c")){
            binding.valueNutriscore.setTextColor(Color.CYAN)

        }
    }
}