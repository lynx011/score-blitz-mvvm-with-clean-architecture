package com.lynx.scoreblitz.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.lynx.scoreblitz.R
import com.lynx.scoreblitz.databinding.FragmentDashboardBinding
import com.lynx.scoreblitz.presentation.adapter.FixturesAdapter
import com.lynx.scoreblitz.presentation.adapter.LeaguesAdapter
import com.lynx.scoreblitz.presentation.view_models.ScoreViewModel
import com.lynx.scoreblitz.utils.ScoreActions
import com.lynx.scoreblitz.utils.collectLatest
import com.lynx.scoreblitz.utils.navigate
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
        viewModel.leagueLiveData.observe(viewLifecycleOwner){leagues ->
            if (viewModel.fixtureList.value == null){
                viewModel.getFixtures(leagues?.get(0)?.league_key!!, viewModel.startDate.value ?: "2022-01-10", viewModel.endDate.value ?: "2023-05-10")
            }
            leaguesAdapter.differ.submitList(leagues ?: emptyList())
            if (leagues.isNullOrEmpty()) binding.leagueShimmer.visibility = View.VISIBLE
            else binding.leagueShimmer.visibility = View.GONE
        }

        viewModel.fixtureList.observe(viewLifecycleOwner){fixtures ->
            if (fixtures.isNullOrEmpty()) binding.fixtureShimmer.visibility = View.VISIBLE
            else binding.fixtureShimmer.visibility = View.GONE
            fixturesAdapter.differ.submitList(fixtures ?: emptyList())
        }

        observeLeagues()
        observeFixtures()
        swipeToRefresh()
        initViewModel()
        observeRoutines()

    }

    @SuppressLint("SuspiciousIndentation")
    private fun setupHeaderLeagues() {
        binding.leagueRec.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        leaguesAdapter = LeaguesAdapter(viewModel) {league ->
            viewModel.selectedLeagueKey.value = league.league_key
            viewModel.leagueLiveData.value?.get(viewModel.key.value?:0)?.league_key?.let { key ->
                viewModel.getFixtures(league.league_key?:152, viewModel.startDate.value ?: "2022-01-10", viewModel.endDate.value ?: "2023-05-10")
            }
        }
        binding.leagueRec.adapter = leaguesAdapter

        binding.fixtureRec.layoutManager = LinearLayoutManager(requireContext())
        fixturesAdapter = FixturesAdapter {result ->
            viewModel.selectedFixture.value = result
            navigate(R.id.nav_fixture_details)

        }
        binding.fixtureRec.adapter = fixturesAdapter
    }

    private fun initViewModel(){
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    @SuppressLint("ResourceType")
    private fun observeRoutines(){
        viewModel.actions.collectLatest(viewLifecycleOwner){ it ->
            when(it){
                ScoreActions.OnPickDate -> {
                    val builder = MaterialDatePicker.Builder.dateRangePicker()
                    val picker = builder.build()
                    picker.show(parentFragmentManager,"DATE_PICKER")
                    picker.addOnNegativeButtonClickListener {
                        picker.dismiss()
                    }
                    picker.addOnPositiveButtonClickListener {
                        val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        viewModel.startDate.value = formatDate.format(Date(it.first)).toString()
                        viewModel.endDate.value = formatDate.format(Date(it.second)).toString()
                        observeLeagues()
                        observeFixtures()
                    }
                    picker.isCancelable = false
                }
            }
        }
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
//                        if (viewModel.fixtures.value.fixtures == null){
//                            it.leagues[0].league_key?.let { it1 -> viewModel.getFixtures(it1) }
//                        }
                        viewModel.leagueLiveData.value = it.leagues

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
                        }
                    }

                    !it.fixtures.isNullOrEmpty() -> {
                        binding.apply {
                            fixtureShimmer.stopShimmer()
                            fixtureShimmer.visibility = View.GONE
                            fixtureRec.visibility = View.VISIBLE
                        }

                        viewModel.fixtureList.value = it.fixtures
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

    private fun swipeToRefresh() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getLeagues()
            viewModel.getFixtures(viewModel.selectedLeagueKey.value?: viewModel.leagueLiveData.value?.get(0)?.league_key
            ?:152,viewModel.startDate.value?:"2022-01-10",viewModel.endDate.value?:"2023-05-10")
            binding.refreshLayout.isRefreshing = false
        }
    }

//    override fun onStart() {
//        super.onStart()
//        binding.leagueShimmer.startShimmer()
//        binding.fixtureShimmer.startShimmer()
//    }
//
//    override fun onPause() {
//        binding.leagueShimmer.stopShimmer()
//        binding.fixtureShimmer.stopShimmer()
//        super.onPause()
//    }

//    override fun onDestroy() {
//        super.onDestroy()
//        coroutineScope.cancel()
//        viewModel.onClear()
//        binding.leagueShimmer.stopShimmer()
//        binding.fixtureShimmer.stopShimmer()
//    }
}