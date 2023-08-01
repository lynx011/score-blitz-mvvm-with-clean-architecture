package com.lynx.scoreblitz.presentation.screens.fixture_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lynx.scoreblitz.R
import com.lynx.scoreblitz.databinding.FragmentStandingsBinding
import com.lynx.scoreblitz.presentation.adapter.StandingsAdapter
import com.lynx.scoreblitz.presentation.view_models.DashboardViewModel
import com.lynx.scoreblitz.presentation.view_models.FixtureDetailsViewModel
import com.lynx.scoreblitz.utils.collectLatest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StandingsFragment : Fragment() {
    private lateinit var binding: FragmentStandingsBinding
    private val detailViewModel: FixtureDetailsViewModel by activityViewModels()
    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    private lateinit var standingsAdapter: StandingsAdapter

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStandingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRec()
        observeStandings()
    }

    private fun setupRec() {
        binding.standingRec.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            standingsAdapter = StandingsAdapter()
            adapter = standingsAdapter
        }
    }

    private fun observeStandings(){
        coroutineScope.launch {
            detailViewModel.standings.collectLatest {
                when{
                    it.loading -> {

                    }
                    !it.standings?.total.isNullOrEmpty() -> {
                        detailViewModel.standingTotal.value = it.standings?.total
                        if (it.standings?.total != null) {
                            binding.notFoundH2H.visibility = View.GONE
                            standingsAdapter.differ.submitList(it.standings.total)
                        } else binding.notFoundH2H.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}