package com.example.musicstreamingapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicstreamingapp.databinding.FragmentHomeBinding
import com.example.musicstreamingapp.presentation.dashboard.DashboardUiState
import com.example.musicstreamingapp.presentation.dashboard.DashboardViewModel
import com.example.musicstreamingapp.presentation.dashboard.EntityAdapter
import com.example.musicstreamingapp.presentation.details.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: EntityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val keypass = requireArguments().getString(ARG_KEYPASS).orEmpty()

        adapter = EntityAdapter { entity ->
            startActivity(
                Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_ENTITY, entity)
                },
            )
        }

        binding.entitiesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.entitiesRecyclerView.adapter = adapter

        binding.retryButton.setOnClickListener { viewModel.retry() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state -> renderState(state) }
            }
        }

        viewModel.loadDashboard(keypass)
    }

    private fun renderState(state: DashboardUiState) {
        when (state) {
            is DashboardUiState.Idle -> Unit
            is DashboardUiState.Loading -> {
                binding.dashboardLoadingOverlay.isVisible = true
                binding.entitiesRecyclerView.isVisible = false
                binding.emptyText.isVisible = false
                binding.errorText.isVisible = false
                binding.retryButton.isVisible = false
            }
            is DashboardUiState.Success -> {
                binding.dashboardLoadingOverlay.isVisible = false
                binding.errorText.isVisible = false
                binding.retryButton.isVisible = false

                val dashboard = state.dashboard
                binding.dashboardTitle.text = getString(R.string.dashboard_languages)
                binding.dashboardSubtitle.text = getString(
                    R.string.dashboard_entity_count,
                    dashboard.entityTotal,
                )

                adapter.submitList(dashboard.entities)
                val hasItems = dashboard.entities.isNotEmpty()
                binding.entitiesRecyclerView.isVisible = hasItems
                binding.emptyText.isVisible = !hasItems
            }
            is DashboardUiState.Error -> {
                binding.dashboardLoadingOverlay.isVisible = false
                binding.entitiesRecyclerView.isVisible = false
                binding.emptyText.isVisible = false
                binding.errorText.isVisible = true
                binding.retryButton.isVisible = true
                binding.errorText.text = state.message
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_KEYPASS = "arg_keypass"

        fun newInstance(keypass: String): HomeFragment {
            return HomeFragment().apply {
                arguments = bundleOf(ARG_KEYPASS to keypass)
            }
        }
    }
}
