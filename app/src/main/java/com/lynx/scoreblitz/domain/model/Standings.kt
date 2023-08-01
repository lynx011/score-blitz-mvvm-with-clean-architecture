package com.lynx.scoreblitz.domain.model

data class StandingList(
    val away: List<StandingAway?>?,
    val home: List<StandingHome?>?,
    val total: List<StandingTotal?>?
)

data class StandingAway(
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
)

data class StandingHome(
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
)

data class StandingTotal(
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
)
