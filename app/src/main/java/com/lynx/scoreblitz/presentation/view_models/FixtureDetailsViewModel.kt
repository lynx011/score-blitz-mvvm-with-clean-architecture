package com.lynx.scoreblitz.presentation.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lynx.scoreblitz.domain.model.H2HModel
import com.lynx.scoreblitz.domain.model.StandingTotal
import com.lynx.scoreblitz.domain.model.Statistic
import com.lynx.scoreblitz.domain.use_cases.FixtureDetailsUseCase
import com.lynx.scoreblitz.presentation.states.H2HStates
import com.lynx.scoreblitz.presentation.states.StandingStates
import com.lynx.scoreblitz.utils.ApiResponse
import com.lynx.scoreblitz.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixtureDetailsViewModel @Inject constructor(private val useCase: FixtureDetailsUseCase) :
    ViewModel() {

    private val _h2h = MutableStateFlow(H2HStates())
    val h2h: StateFlow<H2HStates> = _h2h

    val h2hResult = MutableLiveData<List<H2HModel?>?>()

    val stats = MutableLiveData<List<Statistic?>?>()

    private val _standings = MutableStateFlow(StandingStates())
    val standings: StateFlow<StandingStates> = _standings

    val standingTotal = MutableLiveData<List<StandingTotal?>?>()

    fun getH2H(homeKey: Int?, awayKey: Int?) = viewModelScope.launch(Dispatchers.IO) {
        useCase(met = "H2H", homeKey!!, awayKey!!, Constants.API_KEY).collectLatest {
            when (it) {
                is ApiResponse.Loading -> {
                    _h2h.value = h2h.value.copy(
                        loading = true,
                        h2h = it.data
                    )
                }

                is ApiResponse.Success -> {
                    _h2h.value = h2h.value.copy(
                        loading = false,
                        h2h = it.data
                    )
                }

                is ApiResponse.Error -> {
                    _h2h.value = h2h.value.copy(
                        loading = false,
                        error = it.message ?: "Unexpected Error Occurred!"
                    )
                }
            }
        }
    }

    fun getStandings(leagueId: Int?) = viewModelScope.launch(Dispatchers.IO) {
        useCase(
            met = "Standings",
            leagueId = leagueId!!,
            apiKey = Constants.API_KEY
        ).collectLatest {
            when (it) {
                is ApiResponse.Loading -> {
                    _standings.value = standings.value.copy(
                        loading = true,
                        standings = it.data
                    )
                }

                is ApiResponse.Success -> {
                    _standings.value = standings.value.copy(
                        loading = false,
                        standings = it.data
                    )
                }

                is ApiResponse.Error -> {
                    _standings.value = standings.value.copy(
                        loading = false,
                        error = it.message ?: "Unexpected Error Occurred!"
                    )
                }
            }
        }
    }

    fun onClear() {
        _h2h.value = h2h.value.copy(loading = false, h2h = null, error = "")
        _standings.value = standings.value.copy(loading = false, standings = null, error = "")
        h2hResult.value = emptyList()
        stats.value = emptyList()
        standingTotal.value = null
    }
}