package com.lynx.scoreblitz.presentation.screens.fixture_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lynx.scoreblitz.databinding.FragmentStatsBinding
import com.lynx.scoreblitz.presentation.adapter.StatsAdapter
import com.lynx.scoreblitz.presentation.view_models.DashboardViewModel
import com.lynx.scoreblitz.presentation.view_models.FixtureDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFragment : Fragment() {
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    private lateinit var statsAdapter: StatsAdapter
    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    private val detailViewModel: FixtureDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRec()
        observeStats()
    }

    private fun setupRec() {
        binding.statsRec.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            statsAdapter = StatsAdapter {

            }
            adapter = statsAdapter
        }
    }

    private fun observeStats() {
        dashboardViewModel.fixtureLiveData.observe(viewLifecycleOwner) { fixtures ->
            val filtered =
                fixtures?.find { it?.event_key == dashboardViewModel.selectedFixture.value?.event_key }
            detailViewModel.stats.value = filtered?.statistics
            statsAdapter.differ.submitList(detailViewModel.stats.value)
            binding.notFoundStats.visibility =
                if (detailViewModel.stats.value != null) View.VISIBLE else View.GONE

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}