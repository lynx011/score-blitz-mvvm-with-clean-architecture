package com.lynx.scoreblitz.presentation.states

import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.H2HResponse
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.domain.model.SmModel.SmFixtures
import com.lynx.scoreblitz.domain.model.SmModel.SmLeagues

data class LeaguesStates(
    val loading: Boolean = false,
    val leagues: List<Leagues> = emptyList(),
    val error: String = ""
)

data class FixturesStates(
    val loading: Boolean = false,
    val fixtures: List<FixtureResult?>? = emptyList(),
    val error: String = ""
)

data class SmLeagueStates(
    val loading: Boolean = false,
    val smLeagues: List<SmLeagues?>? = emptyList(),
    val error: String = ""
)

data class SmFixtureStates(
    val loading: Boolean = false,
    val smFixtures: List<SmFixtures?>? = emptyList(),
    val error: String = ""
)

