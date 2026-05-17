package com.example.musicstreamingapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.musicstreamingapp.databinding.ActivityHomeShellBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeShellBinding
    private lateinit var bottomNav: BottomNavigationView
    private var currentMainTab = R.id.nav_home
    private var keypass: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiTheme.applyLightStatusBar(this)
        binding = ActivityHomeShellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        keypass = intent.getStringExtra(EXTRA_KEYPASS).orEmpty()
        if (keypass.isEmpty()) {
            finish()
            return
        }

        bottomNav = binding.bottomNav
        bottomNav.setOnItemSelectedListener { item ->
            val id = item.itemId
            if (id == R.id.nav_profile) {
                startActivity(
                    Intent(this, SettingsActivity::class.java).apply {
                        putExtra(EXTRA_KEYPASS, keypass)
                    },
                )
                bottomNav.post { bottomNav.selectedItemId = currentMainTab }
                return@setOnItemSelectedListener true
            }
            showFragmentFor(id)
            true
        }

        if (savedInstanceState == null) {
            applyTabFromIntent(intent)
        } else {
            currentMainTab = savedInstanceState.getInt(KEY_MAIN_TAB, R.id.nav_home)
            bottomNav.selectedItemId = currentMainTab
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent.getStringExtra(EXTRA_KEYPASS)?.let { keypass = it }
        applyTabFromIntent(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_MAIN_TAB, currentMainTab)
        outState.putString(EXTRA_KEYPASS, keypass)
    }

    fun getKeypass(): String = keypass

    private fun applyTabFromIntent(intent: Intent?) {
        val itemId = resolveItemIdFromIntent(intent)
        bottomNav.selectedItemId = itemId
        showFragmentFor(itemId)
    }

    private fun resolveItemIdFromIntent(intent: Intent?): Int {
        val tab = intent?.getStringExtra(EXTRA_TAB)
        return when (tab) {
            TAB_DISCOVER -> R.id.nav_discover
            TAB_FAVOURITE -> R.id.nav_favourite
            else -> R.id.nav_home
        }
    }

    private fun showFragmentFor(itemId: Int) {
        val fragment: Fragment = when (itemId) {
            R.id.nav_home -> HomeFragment.newInstance(keypass)
            R.id.nav_discover -> DiscoverFragment()
            R.id.nav_favourite -> FavouriteFragment()
            else -> return
        }
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragment_container, fragment)
            .commit()
        currentMainTab = itemId
    }

    companion object {
        const val EXTRA_KEYPASS = "extra_keypass"
        const val EXTRA_TAB = "extra_tab"
        const val TAB_DISCOVER = "discover"
        const val TAB_FAVOURITE = "favourite"
        private const val KEY_MAIN_TAB = "key_main_tab"
    }
}
