package com.codingfactoryprojet.scanneropenfoodfact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ActivityMainBinding
import com.codingfactoryprojet.scanneropenfoodfact.productlist.ProductListActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        binding.bottomNavigation.selectedItemId = R.id.Home

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            item ->
            when (item.itemId) {
                R.id.Home -> {
                    val intent = Intent(this, ProductListActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }
}
