package com.lynx.scoreblitz.presentation.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.domain.use_cases.DashboardUseCase
import com.lynx.scoreblitz.presentation.states.FixturesStates
import com.lynx.scoreblitz.presentation.states.LeaguesStates
import com.lynx.scoreblitz.utils.ApiResponse
import com.lynx.scoreblitz.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val useCase: DashboardUseCase) : ViewModel() {

    private val scope = viewModelScope
    private val _leagues = MutableStateFlow(LeaguesStates())
    val leagues : StateFlow<LeaguesStates> = _leagues

    private val _fixtures = MutableStateFlow(FixturesStates())
    val fixtures : StateFlow<FixturesStates> = _fixtures


    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private var defaultStartDate = dateFormat.format(Date().time - 604800000L)
    private var defaultEndDate = dateFormat.format(Date().time + 604800000L)

    val startDate = MutableLiveData(defaultStartDate)

    val endDate = MutableLiveData(defaultEndDate)

    val leagueLiveData = MutableLiveData<List<Leagues?>?>()

    val selectedLeagueKey = MutableLiveData<Int?>()

    val fixtureLiveData = MutableLiveData<List<FixtureResult?>?>()

    val isSelectedLeague = MutableLiveData(false)

    val selectedLeaguePosition = MutableLiveData<Int?>()

    val selectedFixture = MutableLiveData<FixtureResult?>()

    val key = MutableLiveData<Int?>()

    private val fixturesMap = MutableStateFlow(mutableMapOf<Int,List<FixtureResult?>?>())

//    private var _actions = MutableSharedFlow<ScoreActions>()
//    val actions : SharedFlow<ScoreActions> = _actions
//
//
//    fun onDashboardNav(){
//        _actions.emit(viewModelScope){ ScoreActions.OnPickDate }
//    }

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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onClear(){
        _leagues.value = LeaguesStates(false, emptyList(),"")
        _fixtures.value = FixturesStates(false, emptyList(), "")
        isSelectedLeague.value = false
        selectedLeaguePosition.value = null
        selectedLeagueKey.value = null
        selectedFixture.value = null
        key.value = null
        defaultStartDate = null
        defaultEndDate = null
        startDate.value = null
        endDate.value = null
        leagueLiveData.value = emptyList()
        fixtureLiveData.value = emptyList()
        scope.cancel()
//        _actions.resetReplayCache()
    }

}
