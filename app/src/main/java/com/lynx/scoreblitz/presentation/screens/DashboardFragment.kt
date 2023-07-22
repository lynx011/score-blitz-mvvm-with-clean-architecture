package com.lynx.scoreblitz.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lynx.scoreblitz.databinding.FragmentDashboardBinding
import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.presentation.adapter.FixturesAdapter
import com.lynx.scoreblitz.presentation.adapter.LeaguesAdapter
import com.lynx.scoreblitz.presentation.view_models.ScoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: ScoreViewModel by viewModels()
    private lateinit var leaguesAdapter: LeaguesAdapter
    private lateinit var fixturesAdapter: FixturesAdapter

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
//        observeFixtures()
//        swipeToRefresh()
    }

    private fun setupHeaderLeagues() {
        binding.leagueRec.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        leaguesAdapter = LeaguesAdapter(viewModel) {league ->
            viewModel.selectedLeague.value = league
//            league.league_key?.let { viewModel.getFixtures(it) }
        }
        binding.leagueRec.adapter = leaguesAdapter

//        binding.fixtureRec.layoutManager = LinearLayoutManager(requireContext())
//        fixturesAdapter = FixturesAdapter()
//        binding.fixtureRec.adapter = fixturesAdapter
    }

    private fun observeLeagues() {
        viewModel.getLeagues()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.leagues.collectLatest {
                when {
                    it.loading -> {
//                        binding.progressBar.visibility = View.VISIBLE
                    }

                    it.leagues.isNotEmpty() -> {
//                        binding.progressBar.visibility = View.INVISIBLE
                        leaguesAdapter.differ.submitList(it.leagues.take(6) as ArrayList<Leagues>)
                        it.leagues.map { key ->
                            key.league_key?.let { id -> viewModel.getFixtures(id) }
                        }
                    }

                    it.error.isNotEmpty() -> {
//                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

    }

//    private fun observeFixtures() {
//        CoroutineScope(Dispatchers.Main).launch {
//            viewModel.fixtures.collectLatest {
//                when {
//                    it.loading -> {
//                        binding.progressBar.visibility = View.VISIBLE
 //                   }

//                    !it.fixtures.isNullOrEmpty() -> {
//                        binding.progressBar.visibility = View.INVISIBLE
//                        fixturesAdapter.differ.submitList(it.fixtures.take(50))
//                        Toast.makeText(requireContext(),it.fixtures.toString(),Toast.LENGTH_SHORT).show()
//                    }

//                    it.error.isNotEmpty() -> {
//                        binding.progressBar.visibility = View.VISIBLE
//                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                }
//            }
//        }
//    }

//    private fun swipeToRefresh() {
//        binding.refreshLayout.setOnRefreshListener {
//            observeLeagues()
//            binding.refreshLayout.isRefreshing = false
//        }
//    }
}