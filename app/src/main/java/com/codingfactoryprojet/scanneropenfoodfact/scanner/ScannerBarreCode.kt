package com.codingfactoryprojet.scanneropenfoodfact.scanner

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.codingfactoryprojet.scanneropenfoodfact.databinding.ActivityScannerBinding


private const val CAMERA_REQUEST_CODE = 101

public final class ScannerBarreCode : AppCompatActivity() {
    private lateinit var scannerbarcode: CodeScanner
    private lateinit var binding: ActivityScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPermissions()

        val result:String
        scannerbarcode = CodeScanner(this, binding.scannerView)
        scannerbarcode.camera = CodeScanner.CAMERA_BACK
        scannerbarcode.formats = CodeScanner.ALL_FORMATS

        scannerbarcode.autoFocusMode = AutoFocusMode.SAFE
        scannerbarcode.scanMode = ScanMode.SINGLE

        scannerbarcode.isAutoFocusEnabled = true
        scannerbarcode.isFlashEnabled = false

        scannerbarcode.decodeCallback = DecodeCallback {
            runOnUiThread {
                binding.resultTextView.text = it.text
                
            }
        }

        scannerbarcode.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this@ScannerBarreCode, "Camera initialisation error: ${it.message}", Toast.LENGTH_LONG).show()
            }
        }

        binding.scannerView.setOnClickListener {
            scannerbarcode.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        scannerbarcode.startPreview()
    }

    override fun onPause() {
        scannerbarcode.releaseResources()
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
                        this@ScannerBarreCode,
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
