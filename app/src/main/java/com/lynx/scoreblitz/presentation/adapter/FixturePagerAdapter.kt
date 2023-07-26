package com.lynx.scoreblitz.presentation.adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lynx.scoreblitz.presentation.screens.fixture_detail.H2HFragment
import com.lynx.scoreblitz.presentation.screens.fixture_detail.StatsFragment

class FixturePagerAdapter(fragmentManager: FragmentManager, val lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            H2HFragment()
        else
            StatsFragment()
    }
}