package com.lynx.scoreblitz.presentation.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.domain.use_cases.LeaguesUseCase
import com.lynx.scoreblitz.presentation.states.FixturesStates
import com.lynx.scoreblitz.presentation.states.LeaguesStates
import com.lynx.scoreblitz.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(private val useCase: LeaguesUseCase) : ViewModel() {

    private val _leagues = MutableStateFlow(LeaguesStates())
    val leagues : StateFlow<LeaguesStates> = _leagues

    private val _fixtures = MutableStateFlow(FixturesStates())
    val fixtures : StateFlow<FixturesStates> = _fixtures

    val selectedLeague = MutableLiveData<Leagues>()

    val fixturesMap = mutableMapOf<Int, List<FixtureResult?>?>()

    fun getLeagues() = viewModelScope.launch(Dispatchers.IO) {
        useCase(met = "Leagues", apiKey = "09df73968af6d0c4b72631f2c84024483093115b0a1043c047b2fec418772218").collect{
            when(it){
                is ApiResponse.Loading -> {
                    _leagues.value = leagues.value.copy(
                        loading = true,
                        leagues = it.data ?: emptyList()
                    )
                }
                is ApiResponse.Success -> {
                    _leagues.value = leagues.value.copy(
                        loading = false,
                        leagues = it.data ?: emptyList()
                    )
//                    it.data?.map {id ->
//                        id.league_key?.let {key -> getFixtures(key) }
//                    }
                }
                is ApiResponse.Error -> {
                    _leagues.value = leagues.value.copy(
                        loading = false,
                        error = it.message ?: "An Expected Error Occurred!"
                    )
                }
            }
        }
    }

    fun getFixtures(leagueId: Int) = viewModelScope.launch(Dispatchers.IO) {
        if (!fixturesMap.containsKey(leagueId)){
            useCase(met = "Fixtures", leagueId = leagueId, from = "2022-01-01", to = "2024-01-01", apiKey = "09df73968af6d0c4b72631f2c84024483093115b0a1043c047b2fec418772218").collect{
                when(it){
                    is ApiResponse.Loading -> {
                        _fixtures.value = fixtures.value.copy(
                            loading = true,
                            fixtures = it.data?: emptyList()
                        )
                    }
                    is ApiResponse.Success -> {
                        val fixturesData = it.data ?: emptyList()
                        _fixtures.value = fixtures.value.copy(
                            loading = false,
                            fixtures = fixturesData
                        )
                        // Store fixtures data in the map for the league ID
                        fixturesMap[leagueId] = fixturesData
                    }
                    is ApiResponse.Error -> {
                        _fixtures.value = fixtures.value.copy(
                            loading = false,
                            error = it.message ?: "An Expected Error Occurred!"
                        )
                    }
                }
            }
        }
    }
}