package com.javier.bancodecomercio.ui.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.javier.bancodecomercio.databinding.ActivityMainBinding
import com.javier.bancodecomercio.ui.view.recyclerview.RecyclerClientListAdapter
import com.javier.bancodecomercio.ui.viewmodel.ClientsViewModel
import com.javier.bancodecomercio.utils.showToast
import com.javier.bancodecomercio.utils.startActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var clientModelAdapter: RecyclerClientListAdapter

    private val clientViewModel : ClientsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            clientViewModel.onCreate()
            clientViewModel.listClientModel.observe(this@MainActivity, {
                with(recyclerViewClients) {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    clientModelAdapter = RecyclerClientListAdapter(
                        it,
                        clientListener = {
                            goToClient(it.id)
                        }
                    )
                    adapter = clientModelAdapter
                }
            })
            clientViewModel.isLoading.observe(this@MainActivity, {
                progressBar.isVisible = it
            })
            swipeRefreshLayout.setOnRefreshListener {
                clientViewModel.getRefreshClients()
                recyclerViewClients.adapter?.notifyItemRangeChanged(0, recyclerViewClients.size)
                showToast("Los datos se han actualizado correctamente")
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun goToClient(id: Int) {
        startActivity<ClientDetailsActivity> {
            putExtra(ClientDetailsActivity.ID_CLIENT, id)
        }
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
                        SplashActivity.myLatitude = location.latitude
                        SplashActivity.myLongitude = location.longitude
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
                                moveTaskToBack(true)
                                showToast("Para poder acceder a las funciones de la app debe de activar su GPS")
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
            SplashActivity.REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
        if (shouldProvideRationale) {
            Log.i(SplashActivity.TAG, "Displaying permission rationale to provide additional context.")
        } else {
            Log.i(SplashActivity.TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}