package com.lynx.scoreblitz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.lynx.scoreblitz.databinding.ActivityMainBinding
import com.lynx.scoreblitz.presentation.view_models.FixtureDetailsViewModel
import com.lynx.scoreblitz.presentation.view_models.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val fixtureDetailsViewModel: FixtureDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.dashboard_nav -> {
                    navController.navigate(R.id.nav_dashboard)
                    true
                }
                R.id.news_nav -> {
                    navController.navigate(R.id.nav_news)
                    true
                }
                R.id.watch_nav -> {
                    navController.navigate(R.id.nav_watch)
                    true
                }

                else -> false
            }
        }
    }
}
