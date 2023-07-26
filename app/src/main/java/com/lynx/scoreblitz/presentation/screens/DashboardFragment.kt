package com.lynx.scoreblitz.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lynx.scoreblitz.R
import com.lynx.scoreblitz.databinding.FragmentDashboardBinding
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.presentation.adapter.FixturesAdapter
import com.lynx.scoreblitz.presentation.adapter.LeaguesAdapter
import com.lynx.scoreblitz.presentation.view_models.ScoreViewModel
import com.lynx.scoreblitz.utils.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: ScoreViewModel by activityViewModels()
    private lateinit var leaguesAdapter: LeaguesAdapter
    private lateinit var fixturesAdapter: FixturesAdapter

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeaderLeagues()
        observeLeagues()
        observeFixtures()
        swipeToRefresh()
    }

    private fun setupHeaderLeagues() {
        binding.leagueRec.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        leaguesAdapter = LeaguesAdapter(viewModel) {league ->
//            league.league_key?.let { viewModel.getFixtures(it) }
        }
        binding.leagueRec.adapter = leaguesAdapter

        binding.fixtureRec.layoutManager = LinearLayoutManager(requireContext())
        fixturesAdapter = FixturesAdapter {result ->
            viewModel.selectedFixture.value = result
//            result.league_key?.let { viewModel.getFixtures(it) }
            navigate(R.id.nav_fixture_details)

        }
        binding.fixtureRec.adapter = fixturesAdapter
    }

    private fun observeLeagues() {
        viewModel.getLeagues()
        lifecycleScope.launch {
            viewModel.leagues.collectLatest {
                when {
                    it.loading -> {
                        binding.apply {
                            leagueShimmer.startShimmer()
                            leagueShimmer.visibility = View.VISIBLE
                            leagueRec.visibility = View.GONE
                        }

                    }

                    it.leagues.isNotEmpty() -> {
                        binding.apply {
                            leagueShimmer.stopShimmer()
                            leagueShimmer.visibility = View.GONE
                            leagueRec.visibility = View.VISIBLE
                        }
                        viewModel.key.observe(viewLifecycleOwner){ position ->
                            it.leagues[position?:0].league_key?.let { key -> viewModel.getFixtures(key) }
                        }
                        leaguesAdapter.differ.submitList(it.leagues as ArrayList<Leagues>)
                    }

                    it.error.isNotEmpty() -> {
                        binding.apply {
                            leagueShimmer.startShimmer()
                            leagueShimmer.visibility = View.VISIBLE
                            leagueRec.visibility = View.GONE
                        }
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

    }

    private fun observeFixtures() {
        coroutineScope.launch {
            viewModel.fixtures.collectLatest {
                when {
                    it.loading -> {
                        binding.apply {
                            fixtureShimmer.startShimmer()
                            fixtureShimmer.visibility = View.VISIBLE
                            fixtureRec.visibility = View.GONE
                        }
                    }

                    !it.fixtures.isNullOrEmpty() -> {
                        binding.apply {
                            fixtureShimmer.stopShimmer()
                            fixtureShimmer.visibility = View.GONE
                            fixtureRec.visibility = View.VISIBLE
                        }
                        fixturesAdapter.differ.submitList(it.fixtures)
                    }

                    it.error.isNotEmpty() -> {
                        binding.apply {
                            fixtureShimmer.startShimmer()
                            fixtureShimmer.visibility = View.VISIBLE
                            fixtureRec.visibility = View.GONE
                        }
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun swipeToRefresh() {
        binding.refreshLayout.setOnRefreshListener {
            observeLeagues()
            observeFixtures()
            binding.refreshLayout.isRefreshing = false
        }
    }

    override fun onPause() {
        binding.leagueShimmer.stopShimmer()
        binding.fixtureShimmer.stopShimmer()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        viewModel.onClear()
        binding.leagueShimmer.stopShimmer()
        binding.fixtureShimmer.stopShimmer()
    }
}