package com.lynx.scoreblitz.data.data_sources.dto

import com.lynx.scoreblitz.domain.model.StandingAway
import com.lynx.scoreblitz.domain.model.StandingHome
import com.lynx.scoreblitz.domain.model.StandingList
import com.lynx.scoreblitz.domain.model.StandingTotal

data class StandingsDTO(
    val result: StandingResult,
    val success: Int
)

data class StandingResult(
    val away: List<Away?>?,
    val home: List<Home?>?,
    val total: List<Total?>?
){
    fun toStandingList() : StandingList {
        return StandingList(
            away = away?.map { it?.toStandingAway() },
            home = home?.map { it?.toStandingHome() },
            total = total?.map { it?.toStandingTotal() }
        )
    }
}

data class Away(
    val fk_stage_key: Int?,
    val league_key: Int?,
    val league_round: String?,
    val league_season: String?,
    val stage_name: String?,
    val standing_A: Int?,
    val standing_D: Int?,
    val standing_F: Int?,
    val standing_GD: Int?,
    val standing_L: Int?,
    val standing_P: Int?,
    val standing_PTS: Int?,
    val standing_W: Int?,
    val standing_place: Int?,
    val standing_place_type: Any?,
    val standing_team: String?,
    val standing_updated: String?,
    val team_key: Int?
){
    fun toStandingAway() : StandingAway {
        return StandingAway(
            fk_stage_key = fk_stage_key,
            league_key = league_key,
            league_round = league_round,
            league_season = league_season,
            stage_name = stage_name,
            standing_A = standing_A,
            standing_D = standing_D,
            standing_F = standing_F,
            standing_GD = standing_GD,
            standing_L = standing_L,
            standing_P = standing_P,
            standing_PTS = standing_PTS,
            standing_W = standing_W,
            standing_place = standing_place,
            standing_place_type = standing_place_type,
            standing_team = standing_team,
            standing_updated = standing_updated,
            team_key = team_key
        )
    }
}

data class Home(
    val fk_stage_key: Int?,
    val league_key: Int?,
    val league_round: String?,
    val league_season: String?,
    val stage_name: String?,
    val standing_A: Int?,
    val standing_D: Int?,
    val standing_F: Int?,
    val standing_GD: Int?,
    val standing_L: Int?,
    val standing_P: Int?,
    val standing_PTS: Int?,
    val standing_W: Int?,
    val standing_place: Int?,
    val standing_place_type: Any?,
    val standing_team: String?,
    val standing_updated: String?,
    val team_key: Int?
){
    fun toStandingHome() : StandingHome {
        return StandingHome(
            fk_stage_key = fk_stage_key,
            league_key = league_key,
            league_round = league_round,
            league_season = league_season,
            stage_name = stage_name,
            standing_A = standing_A,
            standing_D = standing_D,
            standing_F = standing_F,
            standing_GD = standing_GD,
            standing_L = standing_L,
            standing_P = standing_P,
            standing_PTS = standing_PTS,
            standing_W = standing_W,
            standing_place = standing_place,
            standing_place_type = standing_place_type,
            standing_team = standing_team,
            standing_updated = standing_updated,
            team_key = team_key
        )
    }
}

data class Total(
    val fk_stage_key: Int?,
    val league_key: Int?,
    val league_round: String?,
    val league_season: String?,
    val stage_name: String?,
    val standing_A: Int?,
    val standing_D: Int?,
    val standing_F: Int?,
    val standing_GD: Int?,
    val standing_L: Int?,
    val standing_P: Int?,
    val standing_PTS: Int?,
    val standing_W: Int?,
    val standing_place: Int?,
    val standing_place_type: Any?,
    val standing_team: String?,
    val standing_updated: String?,
    val team_key: Int?
){
    fun toStandingTotal() : StandingTotal {
        return StandingTotal(
            fk_stage_key = fk_stage_key,
            league_key = league_key,
            league_round = league_round,
            league_season = league_season,
            stage_name = stage_name,
            standing_A = standing_A,
            standing_D = standing_D,
            standing_F = standing_F,
            standing_GD = standing_GD,
            standing_L = standing_L,
            standing_P = standing_P,
            standing_PTS = standing_PTS,
            standing_W = standing_W,
            standing_place = standing_place,
            standing_place_type = standing_place_type,
            standing_team = standing_team,
            standing_updated = standing_updated,
            team_key = team_key
        )
    }
}