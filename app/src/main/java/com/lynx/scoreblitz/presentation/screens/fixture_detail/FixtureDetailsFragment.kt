package com.lynx.scoreblitz.presentation.screens.fixture_detail

import android.os.Bundle
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
import com.lynx.scoreblitz.presentation.adapter.FixturePagerAdapter
import com.lynx.scoreblitz.presentation.view_models.ScoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixtureDetailsFragment : Fragment() {
    private lateinit var binding: FragmentFixtureDetailsBinding
    private val viewModel: ScoreViewModel by activityViewModels()
    private lateinit var pagerAdapter: FixturePagerAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFixtureDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setupViewPager()
        viewModel.getH2H()
    }

    private fun initViewModel(){
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupViewPager(){
        val pagerAdapter = object : FragmentStateAdapter(parentFragmentManager,lifecycle){
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return if (position == 0) H2HFragment()
                else StatsFragment()
            }

        }
        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2

//        pagerAdapter = FixturePagerAdapter(childFragmentManager,lifecycle)

        tabLayout.addTab(tabLayout.newTab().setText("H2H"))
        tabLayout.addTab(tabLayout.newTab().setText("STATS"))

        viewPager2.adapter = pagerAdapter

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null)
                    viewPager2.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
    }
