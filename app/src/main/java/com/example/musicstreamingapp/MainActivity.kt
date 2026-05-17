package com.example.musicstreamingapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.musicstreamingapp.databinding.ActivityMainBinding
import com.example.musicstreamingapp.presentation.login.LoginUiState
import com.example.musicstreamingapp.presentation.login.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiTheme.applyLightStatusBar(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            if (viewModel.uiState.value is LoginUiState.Loading) return@setOnClickListener
            viewModel.login(
                binding.emailInput.text?.toString().orEmpty(),
                binding.passwordInput.text?.toString().orEmpty(),
            )
        }

        binding.emailInput.addTextChangedListener { clearFieldErrors() }
        binding.passwordInput.addTextChangedListener { clearFieldErrors() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    renderState(state)
                }
            }
        }
    }

    private fun renderState(state: LoginUiState) {
        when (state) {
            is LoginUiState.Idle -> {
                binding.loginLoadingOverlay.visibility = View.GONE
                binding.loginButton.isEnabled = true
            }
            is LoginUiState.Loading -> {
                binding.loginLoadingOverlay.visibility = View.VISIBLE
                binding.loginButton.isEnabled = false
            }
            is LoginUiState.Success -> {
                binding.loginLoadingOverlay.visibility = View.GONE
                binding.loginButton.isEnabled = true
                startActivity(AppNavigation.homeIntent(this, state.keypass))
                finish()
                viewModel.resetState()
            }
            is LoginUiState.Error -> {
                binding.loginLoadingOverlay.visibility = View.GONE
                binding.loginButton.isEnabled = true
                binding.passwordLayout.error = state.message
                Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun clearFieldErrors() {
        binding.emailLayout.error = null
        binding.passwordLayout.error = null
    }
}
