package com.lynx.scoreblitz.presentation.states

import com.lynx.scoreblitz.domain.model.H2HResponse

data class H2HStates(
    val loading: Boolean = false,
    val h2h: H2HResponse? = null,
    val error: String = ""
)