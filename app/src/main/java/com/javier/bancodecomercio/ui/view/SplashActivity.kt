package com.javier.bancodecomercio.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.javier.bancodecomercio.R
import com.javier.bancodecomercio.utils.Constants.DELAY
import com.javier.bancodecomercio.utils.startActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    companion object {
        var myLatitude : Double = 0.00
        var myLongitude : Double = 0.00

        const val TAG = "LocationProvider"
        const val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            getLastLocation()
            startActivity<MainActivity>()
        }, DELAY)
    }

    private fun getLastLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location != null) {
                        myLatitude = location.latitude
                        myLongitude = location.longitude
                    } else {
                        val builder = AlertDialog.Builder(this)
                        builder.apply {
                            setMessage("¿Usar localización GPS?").setCancelable(false)
                            setPositiveButton("Si") { _, _ ->
                                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                startActivity(intent)
                            }
                            setNegativeButton("No") { dialog, _ ->
                                dialog.cancel()
                                onBackPressed()
                                Toast.makeText(
                                    this@SplashActivity,
                                    "Para poder acceder a las funciones de la app debe de activar su GPS",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            create()
                            show()
                        }
                    }
                }
        } else {
            startLocationPermissionRequest()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }

    override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
        } else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }
}