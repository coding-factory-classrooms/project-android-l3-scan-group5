package com.codingfactoryprojet.scanneropenfoodfact

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorLong
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ActivityMainBinding
import com.codingfactoryprojet.scanneropenfoodfact.productlist.ProductListActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        binding.bottomNavigation.selectedItemId = R.id.Home

        bottom_navigation.setOnNavigationItemSelectedListener(bottomNavigationListener)
    }

    private val bottomNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
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
