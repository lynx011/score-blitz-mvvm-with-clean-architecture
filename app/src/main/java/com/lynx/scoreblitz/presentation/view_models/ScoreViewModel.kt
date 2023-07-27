package com.lynx.scoreblitz.presentation.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lynx.scoreblitz.domain.model.FirstTeamH2H
import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.H2HModel
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.domain.model.SecondTeamH2H
import com.lynx.scoreblitz.domain.model.Statistic
import com.lynx.scoreblitz.domain.use_cases.LeaguesUseCase
import com.lynx.scoreblitz.presentation.states.FixturesStates
import com.lynx.scoreblitz.presentation.states.H2HStates
import com.lynx.scoreblitz.presentation.states.LeaguesStates
import com.lynx.scoreblitz.utils.ApiResponse
import com.lynx.scoreblitz.utils.Constants
import com.lynx.scoreblitz.utils.ScoreActions
import com.lynx.scoreblitz.utils.emit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(private val useCase: LeaguesUseCase) : ViewModel() {

    private val scope = viewModelScope
    private val _leagues = MutableStateFlow(LeaguesStates())
    val leagues : StateFlow<LeaguesStates> = _leagues

    private val _fixtures = MutableStateFlow(FixturesStates())
    val fixtures : StateFlow<FixturesStates> = _fixtures

    private val _h2h = MutableStateFlow(H2HStates())
    val h2h : StateFlow<H2HStates> = _h2h

    val pickedDate = MutableLiveData<Pair<String?,String?>?>()

    val startDate = MutableLiveData<String?>()

    val endDate = MutableLiveData<String?>()

    val leagueLiveData = MutableLiveData<List<Leagues?>?>()

    val selectedLeagueKey = MutableLiveData<Int?>()

    val fixtureLiveData = MutableLiveData<List<FixtureResult?>?>()

    val isSelectedLeague = MutableLiveData(false)

    val selectedLeaguePosition = MutableLiveData<Int?>()

    val fixtureList = MutableLiveData<List<FixtureResult?>?>()

    val selectedFixture = MutableLiveData<FixtureResult?>()

    val key = MutableLiveData<Int?>()

    val h2hResult = MutableLiveData<List<H2HModel?>?>()

    val h2hFirstTeamResult = MutableLiveData<List<FirstTeamH2H?>?>()

    val h2hSecondTeamResult = MutableLiveData<List<SecondTeamH2H?>?>()

    val stats = MutableLiveData<List<Statistic?>?>()

    private val fixturesMap = MutableStateFlow(mutableMapOf<Int,List<FixtureResult?>?>())

    private var _actions = MutableSharedFlow<ScoreActions>()
    val actions : SharedFlow<ScoreActions> = _actions


    fun onDateRangePicker(){
        _actions.emit(viewModelScope){ ScoreActions.OnPickDate }
    }

    fun getLeagues() = scope.launch(Dispatchers.IO) {
        useCase(met = "Leagues", apiKey = Constants.API_KEY).collectLatest{
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

    fun getFixtures(leagueId: Int, from: String, to: String) = viewModelScope.launch(Dispatchers.IO) {
//        if (!fixturesMap.value.containsKey(leagueId)){
            useCase(met = "Fixtures", leagueId = leagueId, from = from, to = to, apiKey = Constants.API_KEY).collectLatest{
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
//                        fixturesMap.value[leagueId] = it.data
                    }
                    is ApiResponse.Error -> {
                        _fixtures.value = fixtures.value.copy(
                            loading = false,
                            error = it.message ?: "An Expected Error Occurred!"
                        )
                    }
                }
    //        }
        }
    }

    fun getH2H() = viewModelScope.launch(Dispatchers.IO) {
        useCase(met = "H2H", selectedFixture.value?.home_team_key!!, selectedFixture.value?.away_team_key!!, Constants.API_KEY ).collectLatest {
            when(it){
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
                        error = it.message ?: "An Expected Error Occurred!"
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onClear(){
        _leagues.value = LeaguesStates(false, emptyList(),"")
        _fixtures.value = FixturesStates(false, emptyList(), "")
        isSelectedLeague.value = false
        selectedLeaguePosition.value = null
        selectedFixture.value = null
        key.value = null
        h2hResult.value = null
        stats.value = null
        _actions.resetReplayCache()
    }

}
