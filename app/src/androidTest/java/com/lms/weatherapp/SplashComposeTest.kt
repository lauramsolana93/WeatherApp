package com.lms.weatherapp

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.lms.weatherapp.ui.activities.SplashActivityCompose
import com.lms.weatherapp.ui.compose.Logo
import com.lms.weatherapp.ui.theme.WeatherComposeTheme
import org.junit.Rule
import org.junit.Test

class SplashComposeTest {

    @get:Rule
    val composeRule = createAndroidComposeRule(SplashActivityCompose::class.java)


    @Test
    fun checkSplashTest(){
        composeRule.setContent {
            WeatherComposeTheme {
                Surface(color = MaterialTheme.colors.primary){
                    Logo()
                }
            }
        }
        composeRule.onNodeWithContentDescription("Logo")
    }
}