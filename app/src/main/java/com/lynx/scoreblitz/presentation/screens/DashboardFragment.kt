package com.lynx.scoreblitz.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.lynx.scoreblitz.R
import com.lynx.scoreblitz.databinding.FragmentDashboardBinding
import com.lynx.scoreblitz.presentation.adapter.FixturesAdapter
import com.lynx.scoreblitz.presentation.adapter.LeaguesAdapter
import com.lynx.scoreblitz.presentation.view_models.DashboardViewModel
import com.lynx.scoreblitz.presentation.view_models.FixtureDetailsViewModel
import com.lynx.scoreblitz.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by activityViewModels()
    private val detailsViewModel: FixtureDetailsViewModel by activityViewModels()
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
        observeLeaguesAndFixtures()
        initViewModel()
        swipeToRefresh()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val defaultStartDate = dateFormat.format(Date().time - 604800000L)
        viewModel.defaultEndDate.value = defaultStartDate
        val defaultEndDate = dateFormat.format(Date().time + 604800000L)
        viewModel.defaultEndDate.value = defaultEndDate

        binding.pickDate.setOnClickListener {
            pickDateRange()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setupHeaderLeagues() {
        binding.leagueRec.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        leaguesAdapter = LeaguesAdapter(viewModel) { league ->

            if (viewModel.fixtureLiveData.value != null && viewModel.key.value != null) {
                viewModel.key.value = null
                viewModel.fixtureLiveData.value = null
                fixturesAdapter.differ.submitList(emptyList())
                binding.fixtureRec.visibility = View.GONE
            }

            viewModel.selectedLeagueKey.value = league.league_key
            viewModel.getFixtures(
                league.league_key ?: 152,
                viewModel.startDate.value ?: viewModel.defaultStartDate.value ?: "2022-01-10",
                viewModel.endDate.value ?: viewModel.defaultEndDate.value ?: "2023-05-10"
            )
        }
        binding.leagueRec.adapter = leaguesAdapter

        binding.fixtureRec.layoutManager = LinearLayoutManager(requireContext())
        fixturesAdapter = FixturesAdapter { result,home,away ->
            viewModel.selectedFixture.value = result
            //navigate(R.id.nav_fixture_details)
            home.transitionName = "home_logo_${result.league_key}"
            away.transitionName = "away_logo_${result.league_key}"
            val extras = FragmentNavigatorExtras(home to home.transitionName, away to away.transitionName)
            findNavController().navigate(R.id.nav_fixture_details,null,null,extras)

        }
        binding.fixtureRec.adapter = fixturesAdapter
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun observeLeagues() {
        viewModel.getLeagues()
        coroutineScope.launch {
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
                        viewModel.leagueLiveData.value =
                            it.leagues.filter { league -> league.league_key != 1 }
                    }

                    it.error.isNotEmpty() -> {
                        binding.apply {
                            leagueShimmer.startShimmer()
                            leagueShimmer.visibility = View.VISIBLE
                            leagueRec.visibility = View.GONE
                        }
                        toast(it.error)
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
                            notFoundFixtures.visibility = View.GONE
                        }
                    }

                    !it.fixtures.isNullOrEmpty() -> {
                        binding.apply {
                            fixtureShimmer.stopShimmer()
                            fixtureShimmer.visibility = View.GONE
                            fixtureRec.visibility = View.VISIBLE
                            notFoundFixtures.visibility = View.GONE
                        }
                        viewModel.fixtureLiveData.value = it.fixtures
                    }

                    it.fixtures.isNullOrEmpty() -> {
                        binding.apply {
                            fixtureShimmer.stopShimmer()
                            fixtureShimmer.visibility = View.GONE
                            fixtureRec.visibility = View.GONE
                            notFoundFixtures.visibility = View.VISIBLE
                        }
                    }

                    it.error.isNotEmpty() -> {
                        binding.apply {
                            fixtureShimmer.startShimmer()
                            fixtureShimmer.visibility = View.VISIBLE
                            fixtureRec.visibility = View.GONE
                        }
                        toast(it.error)
                    }
                }
            }

        }
    }

    private fun observeLeaguesAndFixtures() {
        viewModel.leagueLiveData.observe(viewLifecycleOwner) { leagues ->
            if (viewModel.fixtureLiveData.value == null) {
                viewModel.getFixtures(
                    leagues?.get(0)?.league_key!!,
                    viewModel.startDate.value ?: viewModel.defaultStartDate.value ?: "2022-01-10",
                    viewModel.endDate.value ?: viewModel.defaultEndDate.value ?: "2023-05-10"
                )
                observeFixtures()
            }
            leaguesAdapter.differ.submitList(leagues ?: emptyList())
            if (leagues.isNullOrEmpty()) binding.leagueShimmer.visibility = View.VISIBLE
            else binding.leagueShimmer.visibility = View.GONE
        }

        viewModel.fixtureLiveData.observe(viewLifecycleOwner) { fixtures ->
            if (fixtures.isNullOrEmpty()) binding.fixtureShimmer.visibility = View.VISIBLE
            else binding.fixtureShimmer.visibility = View.GONE
            fixturesAdapter.differ.submitList(fixtures ?: emptyList())
        }

        if (viewModel.leagueLiveData.value == null) {
            observeLeagues()
        }
    }

//    private fun defaultDate(type: String) : String {
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        return if (type == "start"){
//            dateFormat.format(Date().time - 604800000L).toString()
//        }else dateFormat.format(Date().time + 604800000L).toString()
//    }

    private fun pickDateRange() {
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        builder.setTheme(R.style.DatePickerStyle)
        builder.setTitleText("Set Date Range For Fixtures")
        val picker = builder.build()
        picker.show(parentFragmentManager, "DATE_PICKER")
        picker.addOnNegativeButtonClickListener {
            picker.dismiss()
        }
        picker.addOnPositiveButtonClickListener {
            val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            viewModel.startDate.value = formatDate.format(Date(it.first))
            viewModel.endDate.value = formatDate.format(Date(it.second))
            viewModel.getLeagues()
            viewModel.getFixtures(
                viewModel.selectedLeagueKey.value
                    ?: viewModel.leagueLiveData.value?.get(0)?.league_key
                    ?: 152,
                viewModel.startDate.value ?: viewModel.defaultStartDate.value ?: "2022-01-10",
                viewModel.endDate.value ?: viewModel.defaultEndDate.value ?: "2023-05-10"
            )
            binding.refreshLayout.isRefreshing = false
        }
        picker.isCancelable = true
    }

    private fun swipeToRefresh() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getLeagues()
            viewModel.getFixtures(
                viewModel.selectedLeagueKey.value
                    ?: viewModel.leagueLiveData.value?.get(0)?.league_key
                    ?: 152,
                viewModel.startDate.value ?: viewModel.defaultStartDate.value ?: "2022-01-10",
                viewModel.endDate.value ?: viewModel.defaultEndDate.value ?: "2023-05-10"
            )
            observeLeagues()
            observeFixtures()
            binding.refreshLayout.isRefreshing = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        binding.leagueShimmer.stopShimmer()
        binding.fixtureShimmer.stopShimmer()
        viewModel.onClear()
    }

}
