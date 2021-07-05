package com.lms.weatherapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.karumi.shot.ScreenshotTest
import com.lms.weatherapp.asApp
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

@LargeTest
@RunWith(AndroidJUnit4::class)
abstract class AcceptanceTest<T: Activity>(clazz: Class<T>) : ScreenshotTest {

    @Rule
    @JvmField
    val testRule: IntentsTestRule<T> = IntentsTestRule(clazz, true, false)

    @Before
    fun setup(){
       MockitoAnnotations.initMocks(this)
        val app = InstrumentationRegistry.getInstrumentation().targetContext.asApp()
    }

    fun startActivity(args: Bundle = Bundle()) : T {
        val intent = Intent()
        intent.putExtras(args)
        return testRule.launchActivity(intent)
    }

}