package com.lynx.scoreblitz.presentation.screens.fixture_detail

import android.os.Bundle
import android.os.CountDownTimer
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.lynx.scoreblitz.databinding.FragmentFixtureDetailsBinding
import com.lynx.scoreblitz.presentation.view_models.DashboardViewModel
import com.lynx.scoreblitz.presentation.view_models.FixtureDetailsViewModel
import com.lynx.scoreblitz.utils.navigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixtureDetailsFragment : Fragment() {
    private var _binding: FragmentFixtureDetailsBinding? = null
    private val binding get() = _binding!!

    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    private val detailViewModel: FixtureDetailsViewModel by activityViewModels()
    private lateinit var viewPager2: ViewPager2
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFixtureDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setupViewPager(binding.tabLayout)
        setupViewPager(binding.toolTabLayout)
        detailViewModel.getH2H(dashboardViewModel.selectedFixture.value?.home_team_key,dashboardViewModel.selectedFixture.value?.away_team_key)
        detailViewModel.getStandings(dashboardViewModel.selectedFixture.value?.league_key)
        toolbarVisibility()
        binding.backKey.setOnClickListener {
            navigateUp()
        }
    }

    private fun initViewModel() {
        binding.viewModel = dashboardViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun toolbarVisibility() {
        binding.appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            val totalScrollRange = binding.appBarLayout.totalScrollRange

            val percentage = -verticalOffset / totalScrollRange.toFloat()

            if (percentage == 0f) {
                binding.toolbarResultLayout.visibility = View.GONE
                binding.toolTabLayout.visibility = View.GONE
            } else {
                binding.toolbarResultLayout.visibility = View.VISIBLE
                binding.toolTabLayout.visibility = View.VISIBLE
            }

        }
    }

    private fun setupViewPager(tab: TabLayout) {
        val pagerAdapter = object : FragmentStateAdapter(parentFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> H2HFragment()
                    1 -> StandingsFragment()
                    else -> StatsFragment()
                }
            }

        }

        tab.addTab(tab.newTab().setText("H2H"))
        tab.addTab(tab.newTab().setText("Table"))
        tab.addTab(tab.newTab().setText("Stats"))
        viewPager2 = binding.viewPager2

        viewPager2.adapter = pagerAdapter

        tab.addOnTabSelectedListener(object : OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null)
                    viewPager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tab.selectTab(tab.getTabAt(position))
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        detailViewModel.onClear()
    }
}
