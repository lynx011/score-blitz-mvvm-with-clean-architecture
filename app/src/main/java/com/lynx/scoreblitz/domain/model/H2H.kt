package com.lynx.scoreblitz.domain.model

import com.lynx.scoreblitz.data.data_sources.dto.FirstTeamResult
import com.lynx.scoreblitz.data.data_sources.dto.H2H
import com.lynx.scoreblitz.data.data_sources.dto.H2HResult
import com.lynx.scoreblitz.data.data_sources.dto.SecondTeamResult

data class H2HResponse(
    val H2H: List<H2HModel?>?,
    val firstTeamResults: List<FirstTeamH2H?>?,
    val secondTeamResults: List<SecondTeamH2H?>?
)

data class H2HModel(
    val away_team_key: Int?,
    val country_name: String?,
    val event_away_team: String?,
    val event_country_key: Int?,
    val event_date: String?,
    val event_final_result: String?,
    val event_halftime_result: String?,
    val event_home_team: String?,
    val event_key: Int?,
    val event_live: String?,
    val event_status: String?,
    val event_time: String?,
    val home_team_key: Int?,
    val league_key: Int?,
    val league_name: String?,
    val league_round: String?,
    val league_season: String?
)

data class FirstTeamH2H(
    val away_team_key: Int?,
    val country_name: String?,
    val event_away_team: String?,
    val event_country_key: Int?,
    val event_date: String?,
    val event_final_result: String?,
    val event_halftime_result: String?,
    val event_home_team: String?,
    val event_key: Int?,
    val event_live: String?,
    val event_status: String?,
    val event_time: String?,
    val home_team_key: Int?,
    val league_key: Int?,
    val league_name: String?,
    val league_round: String?,
    val league_season: String?
)

data class SecondTeamH2H(
    val away_team_key: Int?,
    val country_name: String?,
    val event_away_team: String?,
    val event_country_key: Int?,
    val event_date: String?,
    val event_final_result: String?,
    val event_halftime_result: String?,
    val event_home_team: String?,
    val event_key: Int?,
    val event_live: String?,
    val event_status: String?,
    val event_time: String?,
    val home_team_key: Int?,
    val league_key: Int?,
    val league_name: String?,
    val league_round: String?,
    val league_season: String?
)