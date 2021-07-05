package com.lms.weatherapp.ui

import com.lms.weatherapp.ui.views.MainActivity
import org.junit.Test

class MainActivityTest: AcceptanceTest<MainActivity>(MainActivity::class.java) {


    @Test
    fun showMainActivity(){

        val activity = startActivity()
        compareScreenshot(activity)
    }


}