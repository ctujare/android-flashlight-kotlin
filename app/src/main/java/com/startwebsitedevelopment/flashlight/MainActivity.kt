package com.startwebsitedevelopment.flashlight

import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if Flash is available
        var isFlashAvailable : Boolean = applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

        if(!isFlashAvailable){
            noFlashError()
        }
        // Toast.makeText(this, "Flash Available : $isFlashAvailable", Toast.LENGTH_LONG).show()
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        try {
            cameraID = cameraManager.cameraIdList[0]
        }catch (e: CameraAccessException){
            e.printStackTrace()
        }

        switchToggle.setOnCheckedChangeListener{_, isChecked -> switchFlashLight(isChecked)}
    }

    private fun noFlashError(){
        val alert = AlertDialog.Builder(this)
            .create()
        alert.setTitle("Oops!")
        alert.setMessage("Flash not available in this device...")
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _, _ -> finish() }
        alert.show()
    }

    private fun switchFlashLight(status: Boolean){
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                cameraManager.setTorchMode(cameraID, status)
            }
        }catch (e: CameraAccessException){
            e.printStackTrace()
        }
    }
}