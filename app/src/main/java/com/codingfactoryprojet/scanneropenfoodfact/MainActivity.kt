package com.codingfactoryprojet.scanneropenfoodfact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codingfactoryprojet.scanneropenfoodfact.product.getProductData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getProductData(737628064502)
    }
}