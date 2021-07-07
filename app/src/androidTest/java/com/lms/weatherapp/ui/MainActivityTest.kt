package com.lms.weatherapp.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.lms.weatherapp.ui.views.MainActivity
import com.lms.wheatherapp.R
import org.junit.Test


class MainActivityTest: AcceptanceTest<MainActivity>(MainActivity::class.java) {


    @Test
    fun showMainActivity(){

        val activity = startActivity()
        onView(withId(R.id.hourly_icon)).check(matches(isDisplayed()))
        onView(withId(R.id.weather_location)).check(matches(isDisplayed()))
        onView(withId(R.id.weatherIcon)).check(matches(isDisplayed()))
        compareScreenshot(activity)

    }


}