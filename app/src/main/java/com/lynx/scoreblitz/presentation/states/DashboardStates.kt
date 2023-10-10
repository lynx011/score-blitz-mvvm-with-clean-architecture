package com.lynx.scoreblitz.presentation.states

import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.Leagues

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
