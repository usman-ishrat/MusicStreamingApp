package com.example.musicstreamingapp

import android.content.Context
import android.content.Intent

object AppNavigation {
    @JvmStatic
    fun homeIntent(context: Context, keypass: String): Intent {
        return Intent(context, HomeActivity::class.java).apply {
            putExtra(HomeActivity.EXTRA_KEYPASS, keypass)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
    }

    @JvmStatic
    fun homeIntent(context: Context, keypass: String, tab: String?): Intent {
        return homeIntent(context, keypass).apply {
            tab?.let { putExtra(HomeActivity.EXTRA_TAB, it) }
        }
    }
}
