package com.codingfactoryprojet.scanneropenfoodfact.scanner

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ActivityScannerBinding


private const val CAMERA_REQUEST_CODE = 101

class BarCodeScannerActivity : AppCompatActivity() {
    private lateinit var barcodeScanner: CodeScanner
    private lateinit var binding: ActivityScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPermissions()

        barcodeScanner = CodeScanner(this, binding.scannerView)
        barcodeScanner.camera = CodeScanner.CAMERA_BACK
        barcodeScanner.formats = CodeScanner.ALL_FORMATS

        barcodeScanner.autoFocusMode = AutoFocusMode.SAFE
        barcodeScanner.scanMode = ScanMode.SINGLE

        barcodeScanner.isAutoFocusEnabled = true
        barcodeScanner.isFlashEnabled = false

        barcodeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                binding.resultTextView.text = it.text
                handleBarCode(it.text)
            }
        }

        barcodeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this@BarCodeScannerActivity, "Camera initialisation error: ${it.message}", Toast.LENGTH_LONG).show()
            }
        }


        binding.scannerView.setOnClickListener {
            barcodeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        barcodeScanner.startPreview()
    }

    override fun onPause() {
        barcodeScanner.releaseResources()
        super.onPause()
    }

    fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    fun makeRequest() {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    fun handleBarCode(barcode: String) {
        val returnIntent = Intent()
        returnIntent.putExtra("barcode", barcode)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this@BarCodeScannerActivity,
                        "Need permission to access camera",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //Success
                }
            }
        }
    }
}
