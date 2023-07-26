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
import com.lynx.scoreblitz.presentation.view_models.ScoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFragment : Fragment() {
    private lateinit var binding: FragmentStatsBinding
    private lateinit var statsAdapter: StatsAdapter
    private val viewModel: ScoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatsBinding.inflate(inflater, container, false)
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
        val filtered =
            viewModel.fixtureList.value?.find { it?.home_team_key == viewModel.selectedFixture.value?.home_team_key
                    && it?.away_team_key == viewModel.selectedFixture.value?.away_team_key
                    && it?.event_key == viewModel.selectedFixture.value?.event_key
                    && it?.event_date == viewModel.selectedFixture.value?.event_date
                    && it?.event_time == viewModel.selectedFixture.value?.event_time}
        viewModel.stats.value = filtered?.statistics
        statsAdapter.differ.submitList(viewModel.stats.value)
    }
}