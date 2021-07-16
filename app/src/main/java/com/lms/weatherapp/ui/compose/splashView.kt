package com.lms.weatherapp.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.lms.wheatherapp.R

@Composable
fun Logo(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.size(100.dp)){
            /*val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.weather_animation) }
            LottieAnimation(
                animationSpec,
                modifier = Modifier.size(100.dp)
            )*/
            Image(
                ImageVector.vectorResource(
                    id = R.drawable.ic_cloudy
                ),
                "Logo",
            )
        }

    }
}
