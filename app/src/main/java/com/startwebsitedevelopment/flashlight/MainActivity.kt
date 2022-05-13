package com.startwebsitedevelopment.flashlight

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if Flash is available
        val isFlashAvailable : Boolean = applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

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

        switchFlashLight(true)

        switchToggle.isChecked = true
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.suggestion_item -> {
                val intent = Intent(this, SuggestionForm::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}