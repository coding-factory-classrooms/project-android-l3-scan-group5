package com.codingfactoryprojet.scanneropenfoodfact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ActivityHomeBinding
import com.codingfactoryprojet.scanneropenfoodfact.scanner.ScannerBarreCode


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.scanButton.setOnClickListener{
            scanCode()
        }
    }

    private fun scanCode(){
        val intent = Intent(this@HomeActivity, ScannerBarreCode::class.java)
        startActivity(intent)
    }
}
