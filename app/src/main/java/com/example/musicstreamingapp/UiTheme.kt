package com.example.musicstreamingapp

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat

object UiTheme {
    @JvmStatic
    fun applyLightStatusBar(activity: AppCompatActivity) {
        WindowCompat.setDecorFitsSystemWindows(activity.window, true)
        activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.white)
        WindowCompat.getInsetsController(activity.window, activity.window.decorView)
            .isAppearanceLightStatusBars = true
    }
}
