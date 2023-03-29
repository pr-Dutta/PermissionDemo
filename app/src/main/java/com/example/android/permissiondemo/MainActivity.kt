package com.example.android.permissiondemo

import android.Manifest
import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private val cameraResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()) {
                isGrantec ->
                if (isGrantec) {
                    Toast.makeText(this,
                        "Permission granted for camera", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this,
                        "Permission denied for camera", Toast.LENGTH_SHORT).show()
                }
            }

    private val cameraAndLocationResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) {
                permissions ->
                permissions.entries.forEach {
                    val permissionName = it.key
                    val isGranted = it.value
                    if (isGranted) {
                        if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                            Toast.makeText(this,
                            "Permission granted for location", Toast.LENGTH_SHORT).show()
                        }else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) {
                            Toast.makeText(this,
                                "Permission granted for Coarse location", Toast.LENGTH_SHORT).show()
                        }else {
                            Toast.makeText(this,
                                "Permission granted for Camera", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                            Toast.makeText(this,
                                "Permission denied for location", Toast.LENGTH_SHORT).show()
                        }else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) {
                            Toast.makeText(this,
                                "Permission denied for Coarse location", Toast.LENGTH_SHORT).show()
                        }else {
                            Toast.makeText(this,
                                "Permission denied for Camera", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCameraPermission: Button = findViewById(R.id.btnCameraPermission)
        btnCameraPermission.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                showRationaleDialog("Permission Demo request Camera access",
                    "Camera can not be user because Camera access is denied")
            }else {
                cameraAndLocationResultLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                )
            }
        }
    }


    /*
    * Shows rational dialog for displaying why the app needs permission
    * Only show if the user has denied the permission request previously
    */

    private fun showRationaleDialog(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") {dialog, _->
                dialog.dismiss()
            }
        builder.create().show()
    }
}








