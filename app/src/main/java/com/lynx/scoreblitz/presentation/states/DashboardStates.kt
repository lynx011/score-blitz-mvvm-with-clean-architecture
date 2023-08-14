package com.lynx.scoreblitz.presentation.states

import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.domain.model.blitz_model.FixtureData

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

data class ScoreStates(
    val loading: Boolean = false,
    val scores: List<FixtureData?>? = emptyList(),
    val error: String = ""
)

