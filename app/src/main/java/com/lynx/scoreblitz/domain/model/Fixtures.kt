package com.lynx.scoreblitz.domain.model

import com.lynx.scoreblitz.data.data_sources.dto.FixtureResultDTO

data class FixtureResult(
    val away_team_key: Int?,
    val away_team_logo: String?,
    val cards: List<Card?>?,
    val country_logo: String?,
    val country_name: String?,
    val event_away_formation: String?,
    val event_away_team: String?,
    val event_country_key: Int?,
    val event_date: String?,
    val event_final_result: String?,
    val event_ft_result: String?,
    val event_halftime_result: String?,
    val event_home_formation: String?,
    val event_home_team: String?,
    val event_key: Int?,
    val event_live: String?,
    val event_penalty_result: String?,
    val event_referee: String?,
    val event_stadium: String?,
    val event_status: String?,
    val event_time: String?,
    val fk_stage_key: Int?,
    val goalscorers: List<Goalscorer?>?,
    val home_team_key: Int?,
    val home_team_logo: String?,
    val league_group: Any?,
    val league_key: Int?,
    val league_logo: String?,
    val league_name: String?,
    val league_round: String?,
    val league_season: String?,
    val lineups: Lineups?,
    val stage_name: String?,
    val statistics: List<Statistic?>?,
    val substitutes: List<SubstituteXX?>?
)

data class Card(
    val away_fault: String?,
    val away_player_id: String?,
    val card: String?,
    val home_fault: String?,
    val home_player_id: String?,
    val info: String?,
    val info_time: String?,
    val time: String?
)

data class Goalscorer(
//    val away_assist: String?,
    val away_assist_id: String?,
//    val away_scorer: String?,
    val away_scorer_id: String?,
//    val home_assist: String?,
    val home_assist_id: String?,
//    val home_scorer: String?,
    val home_scorer_id: String?,
    val info: String?,
    val info_time: String?,
    val score: String?,
    val time: String?
)

data class Lineups(
    val away_team: AwayTeam?,
    val home_team: HomeTeam?
)

data class HomeTeam(
    val coaches: List<Coach?>?,
    val missing_players: List<Any?>?,
    val starting_lineups: List<StartingLineup?>?,
    val substitutes: List<Substitute?>?
)

data class Coach(
    val coach: String?,
    val coach_country: Any?
)

data class StartingLineup(
    val info_time: String?,
    val player: String?,
    val player_country: Any?,
    val player_key: Long?,
    val player_number: Int?,
    val player_position: Int?
)

data class AwayTeam(
    val coaches: List<Coach?>?,
    val missing_players: List<Any?>?,
    val starting_lineups: List<StartingLineup?>?,
    val substitutes: List<Substitute?>?
)

data class Substitute(
    val info_time: String?,
    val player: String?,
    val player_country: Any?,
    val player_key: Long?,
    val player_number: Int?,
    val player_position: Int?
)

data class Statistic(
    val away: String?,
    val home: String?,
    val type: String?
)

data class SubstituteXX(
    val away_assist: Any?,
//    val away_scorer: AwayScorer,
    val home_assist: Any?,
//    val home_scorer: HomeScorer,
    val info: Any?,
    val info_time: String?,
    val score: String?,
    val time: String?
)

data class AwayScorer(
    val `in`: String?,
    val in_id: Long?,
    val `out`: String?,
    val out_id: Long?
)

data class HomeScorer(
    val `in`: String?,
    val in_id: Int?,
    val `out`: String?,
    val out_id: Long?
)