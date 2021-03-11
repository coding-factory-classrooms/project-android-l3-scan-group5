package com.codingfactoryprojet.scanneropenfoodfact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ActivityAccueilBinding
import com.codingfactoryprojet.scanneropenfoodfact.scanner.ScannerBarreCodebis


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccueilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.scanButton.setOnClickListener{
            scanCode()

        }
    }

    private fun scanCode(){
        val intent = Intent(this@Accueil, ScannerBarreCodebis::class.java)
        startActivity(intent)
    }
}
