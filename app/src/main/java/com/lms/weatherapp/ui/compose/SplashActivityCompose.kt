package com.lms.weatherapp.ui.compose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lms.weatherapp.WeatherApplication
import com.lms.weatherapp.location.viewmodel.LocationViewModel
import com.lms.weatherapp.location.viewmodel.LocationViewModelFactory
import com.lms.weatherapp.ui.commons.BaseActivity
import com.lms.weatherapp.ui.theme.WeatherComposeTheme
import com.lms.weatherapp.ui.views.MainActivity
import com.lms.wheatherapp.R

class SplashActivityCompose : BaseActivity() {

    private lateinit var viewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherComposeTheme {
                Surface(color = MaterialTheme.colors.primary){
                    Logo()
                }
            }
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        checkLocationPermission()
        initRepos()
    }
    private fun initRepos() {
        val repository = (application as WeatherApplication).locationRepository
        val factory = (application as WeatherApplication).locationFactory
        viewModel = ViewModelProvider(this, LocationViewModelFactory(repository, factory)).get(
            LocationViewModel::class.java
        )

        viewModel.getLocation().observe(this, {
            startActivity(Intent(this, MainActivity::class.java))
        })

        viewModel.getError().observe(this, {
            it.let {
                MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.location_not_found))
                    .setMessage(getString(R.string.location_try_again))
                    .setPositiveButton(getString(R.string.accept_permission)){ _, _ ->
                        checkLocationPermission()
                    }
                    .setOnDismissListener { checkLocationPermission() }
                    .show()
            }
        })

    }

    override fun permissionGranted() {
        super.permissionGranted()
        if(location != null){
            Log.e("SPLASH", "HELLO: $location")
            viewModel.initLocation(location ?: "")
        }
    }
}

@Composable
fun Logo(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.size(100.dp)){
            Image(
                ImageVector.vectorResource(
                    id = R.drawable.ic_cloudy
                ),
                "Localized description",
            )
        }
    }


}
@Preview
@Composable
fun LogoPreview(){
    WeatherComposeTheme {
        Logo()
    }
}