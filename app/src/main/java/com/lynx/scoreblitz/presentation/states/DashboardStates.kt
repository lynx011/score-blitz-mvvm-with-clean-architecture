package com.lynx.scoreblitz.presentation.states

import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.domain.model.SmModel.SmFixtureList
import com.lynx.scoreblitz.domain.model.SmModel.SmLeagueList

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
    val smLeagues: List<SmLeagueList?>? = emptyList(),
    val error: String = ""
)

data class SmFixtureStates(
    val loading: Boolean = false,
    val smFixtures: List<SmFixtureList?>? = emptyList(),
    val error: String = ""
)

