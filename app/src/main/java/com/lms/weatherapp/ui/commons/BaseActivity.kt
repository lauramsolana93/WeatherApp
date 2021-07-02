package com.lms.weatherapp.ui.commons

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lms.wheatherapp.R

abstract class BaseActivity : AppCompatActivity() {
    protected var location: String? = null

    companion object{
        private val REQUIRED_PERMISSIONS =
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0){
            getLocation()
        } else {
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.location_permission_title))
                .setMessage(getString(R.string.location_permission_accept_text))
                .setPositiveButton(getString(R.string.accept_permission)) { _, _ ->
                    checkLocationPermission()
                }
                .setNegativeButton(getString(R.string.cancel_permission)) { _, _ ->
                    MaterialAlertDialogBuilder(this)
                        .setTitle(getString(R.string.location_permission_title))
                        .setMessage(getString(R.string.location_permission_text))
                        .setPositiveButton(getString(R.string.accept_permission)){ _, _ ->
                            checkLocationPermission()
                        }
                        .setNegativeButton(getString(R.string.cancel_permission)){ _, _ ->
                            finish()
                        }
                        .show()
                }
                .show()
        }
    }

    private fun getLocation(){
        getFusedLocationProviderClient().lastLocation.addOnSuccessListener { location ->
            if(location != null){
                this.location = "${location.latitude},${location.longitude}"
                permissionGranted()

            } else {
                if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,5000,0F
                    ) { currentLocation ->
                        this.location ="${currentLocation.latitude},${currentLocation.longitude}"
                        permissionGranted()
                    }
                }
            }
        }
    }

    fun checkLocationPermission() {
        if(allPermissionGranted()){
            getLocation()
        } else {
            ActivityCompat.requestPermissions(this,
                REQUIRED_PERMISSIONS, 0)
        }
    }

    private fun getFusedLocationProviderClient() =
        LocationServices.getFusedLocationProviderClient(this)


    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    open fun permissionGranted(){
    }

}