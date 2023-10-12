package com.lynx.scoreblitz.presentation.screens.fixture_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lynx.scoreblitz.databinding.FragmentStandingsBinding
import com.lynx.scoreblitz.presentation.adapter.StandingsAdapter
import com.lynx.scoreblitz.presentation.view_models.DashboardViewModel
import com.lynx.scoreblitz.presentation.view_models.FixtureDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StandingsFragment : Fragment() {
    private var _binding: FragmentStandingsBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: FixtureDetailsViewModel by activityViewModels()
    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    private lateinit var standingsAdapter: StandingsAdapter

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStandingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRec()
        observeStandings()
    }

    private fun setupRec() {
        binding.standingRec.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            standingsAdapter = StandingsAdapter(dashboardViewModel)
            adapter = standingsAdapter
        }
    }

    private fun observeStandings() {
        coroutineScope.launch {
            detailViewModel.standings.collectLatest {
                when {
                    it.loading -> {
                        binding.apply {
                            leagueTypeColumn.visibility = View.GONE
                            standingHead.visibility = View.GONE
                        }
                    }

                    !it.standings?.total.isNullOrEmpty() -> {
                        binding.standingsCard.visibility = View.VISIBLE
                        binding.standingRec.visibility = View.VISIBLE
                        detailViewModel.standingTotal.value = it.standings?.total
                        if (it.standings?.total != null) {
                            binding.notFoundH2H.visibility = View.GONE
                            standingsAdapter.differ.submitList(it.standings.total)
                        } else binding.notFoundH2H.visibility = View.VISIBLE
                        binding.standingHead.visibility = View.VISIBLE
                        binding.leagueTypeColumn.visibility = View.VISIBLE
                    }

                    it.standings?.total.isNullOrEmpty() -> {
                        binding.apply {
                            standingsCard.visibility = View.GONE
                            standingRec.visibility = View.GONE
                            standingHead.visibility = View.GONE
                            leagueTypeColumn.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}