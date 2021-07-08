package com.lms.weatherapp.ui

import org.junit.Test

class SplashActivityTest: AcceptanceTest<SplashActivity>(SplashActivity::class.java) {

    @Test
    fun showSplashActivity(){
        val activity = startActivity()
        compareScreenshot(activity)
    }
}