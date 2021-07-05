package com.lms.weatherapp.ui

import android.app.Activity
import android.content.Context
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers

interface ScreenshotTest {
    fun compareScreenshot(activity: Activity){
        Screenshot.snapActivity(activity).record()
    }

    fun compareScreenshot(view: View, height: Int){
        val context = getInstrumentation().targetContext
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(metrics)
        ViewHelpers.setupView(view)
            .setExactHeightPx(context.resources.getDimensionPixelSize(height))
            .setExactWidthPx(metrics.widthPixels)
            .layout()
        Screenshot.snap(view).record()
    }
}