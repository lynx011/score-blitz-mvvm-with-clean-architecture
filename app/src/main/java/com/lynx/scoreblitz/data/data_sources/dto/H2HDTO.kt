package com.lynx.scoreblitz.data.data_sources.dto

import com.lynx.scoreblitz.domain.model.FirstTeamH2H
import com.lynx.scoreblitz.domain.model.H2HModel
import com.lynx.scoreblitz.domain.model.H2HResponse
import com.lynx.scoreblitz.domain.model.SecondTeamH2H

data class H2HDTO(
    val result: H2HResult,
    val success: Int
)

data class H2HResult(
    val H2H: List<H2H?>?,
    val firstTeamResults: List<FirstTeamResult?>?,
    val secondTeamResults: List<SecondTeamResult?>?
){
    fun toH2HList() : H2HResponse{
        return H2HResponse(
            H2H = H2H?.map { it?.toH2HModel() },
            firstTeamResults = firstTeamResults?.map { it?.toFirstTeamH2H() },
            secondTeamResults = secondTeamResults?.map { it?.toSecondTeamH2H() }
        )
    }
}

data class H2H(
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
) {
    fun toH2HModel(): H2HModel {
        return H2HModel(
            away_team_key = away_team_key,
            country_name = country_name,
            event_away_team = event_away_team,
            event_country_key = event_country_key,
            event_date = event_date,
            event_final_result = event_final_result,
            event_halftime_result = event_halftime_result,
            event_home_team = event_home_team,
            event_key = event_key,
            event_live = event_live,
            event_status = event_status,
            event_time = event_time,
            home_team_key = home_team_key,
            league_key = league_key,
            league_name = league_name,
            league_round = league_round,
            league_season = league_season
        )
    }
}

data class FirstTeamResult(
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
) {
    fun toFirstTeamH2H(): FirstTeamH2H {
        return FirstTeamH2H(
            away_team_key = away_team_key,
            country_name = country_name,
            event_away_team = event_away_team,
            event_country_key = event_country_key,
            event_date = event_date,
            event_final_result = event_final_result,
            event_halftime_result = event_halftime_result,
            event_home_team = event_home_team,
            event_key = event_key,
            event_live = event_live,
            event_status = event_status,
            event_time = event_time,
            home_team_key = home_team_key,
            league_key = league_key,
            league_name = league_name,
            league_round = league_round,
            league_season = league_season
        )
    }
}

data class SecondTeamResult(
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
) {
    fun toSecondTeamH2H(): SecondTeamH2H {
        return SecondTeamH2H(
            away_team_key = away_team_key,
            country_name = country_name,
            event_away_team = event_away_team,
            event_country_key = event_country_key,
            event_date = event_date,
            event_final_result = event_final_result,
            event_halftime_result = event_halftime_result,
            event_home_team = event_home_team,
            event_key = event_key,
            event_live = event_live,
            event_status = event_status,
            event_time = event_time,
            home_team_key = home_team_key,
            league_key = league_key,
            league_name = league_name,
            league_round = league_round,
            league_season = league_season
        )
    }
}