package com.lynx.scoreblitz.data.data_sources.dto

import com.lynx.scoreblitz.domain.model.AwayScorer
import com.lynx.scoreblitz.domain.model.AwayTeam
import com.lynx.scoreblitz.domain.model.Card
import com.lynx.scoreblitz.domain.model.Coach
import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.Goalscorer
import com.lynx.scoreblitz.domain.model.HomeScorer
import com.lynx.scoreblitz.domain.model.HomeTeam
import com.lynx.scoreblitz.domain.model.Lineups
import com.lynx.scoreblitz.domain.model.StartingLineup
import com.lynx.scoreblitz.domain.model.Statistic
import com.lynx.scoreblitz.domain.model.Substitute
import com.lynx.scoreblitz.domain.model.SubstituteXX

data class FixturesDTO(
    val result: List<FixtureResultDTO>,
    val success: Int
)

data class FixtureResultDTO(
    val away_team_key: Int?,
    val away_team_logo: String?,
    val cards: List<CardDTO?>,
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
    val goalscorers: List<GoalscorerDTO?>?,
    val home_team_key: Int?,
    val home_team_logo: String?,
    val league_group: Any?,
    val league_key: Int?,
    val league_logo: String?,
    val league_name: String?,
    val league_round: String?,
    val league_season: String?,
    val lineups: LineupsDTO?,
    val stage_name: String?,
    val statistics: List<StatisticDTO?>?,
    val substitutes: List<SubstituteXXDTO?>?
) {
    fun toFixtureResult(): FixtureResult {
        return FixtureResult(
            away_team_key = away_team_key,
            away_team_logo = away_team_logo,
            cards = cards.map { it?.toCard() },
            country_logo = country_logo,
            country_name = country_name,
            event_away_formation = event_away_formation,
            event_away_team = event_away_team,
            event_country_key = event_country_key,
            event_date = event_date,
            event_final_result = event_final_result,
            event_ft_result = event_ft_result,
            event_halftime_result = event_halftime_result,
            event_home_formation = event_home_formation,
            event_home_team = event_home_team,
            event_key = event_key,
            event_live = event_live,
            event_penalty_result = event_penalty_result,
            event_referee = event_referee,
            event_stadium = event_stadium,
            event_status = event_status,
            event_time = event_time,
            fk_stage_key = fk_stage_key,
            goalscorers = goalscorers?.map { it?.toGoalscorer() },
            home_team_key = home_team_key,
            home_team_logo = home_team_logo,
            league_group = league_group,
            league_key = league_key,
            league_logo = league_logo,
            league_name = league_name,
            league_round = league_round,
            league_season = league_season,
            lineups = lineups?.toLineups(),
            stage_name = stage_name,
            statistics = statistics?.map { it?.toStatistic() },
            substitutes = substitutes?.map { it?.toSubstituteXX() }
        )
    }
}

data class CardDTO(
    val away_fault: String?,
    val away_player_id: String?,
    val card: String?,
    val home_fault: String?,
    val home_player_id: String?,
    val info: String?,
    val info_time: String?,
    val time: String?
){
    fun toCard() : Card {
        return Card(
            away_fault = away_fault,
            away_player_id = away_player_id,
            card = card,
            home_fault = home_fault,
            home_player_id = home_player_id,
            info = info,
            info_time = info_time,
            time = time
        )
    }
}

data class GoalscorerDTO(
    val away_assist: String?,
    val away_assist_id: String?,
    val away_scorer: String?,
    val away_scorer_id: String?,
    val home_assist: String?,
    val home_assist_id: String?,
    val home_scorer: String?,
    val home_scorer_id: String?,
    val info: String?,
    val info_time: String?,
    val score: String?,
    val time: String?
){
    fun toGoalscorer() : Goalscorer{
        return Goalscorer(
//            away_assist = away_assist,
            away_assist_id = away_assist_id,
//            away_scorer = away_scorer,
            away_scorer_id = away_scorer_id,
//            home_assist = home_assist,
            home_assist_id = home_assist_id,
//            home_scorer = home_scorer,
            home_scorer_id = home_scorer_id,
            info = info,
            info_time = info_time,
            score = score,
            time = time
        )
    }
}

data class LineupsDTO(
    val away_team: AwayTeamDTO?,
    val home_team: HomeTeamDTO?
){
    fun toLineups() : Lineups{
        return Lineups(
            away_team = away_team?.toAwayTeam(),
            home_team = home_team?.toHomeTeam()
        )
    }
}

data class HomeTeamDTO(
    val coaches: List<CoachDTO?>?,
    val missing_players: List<Any?>?,
    val starting_lineups: List<StartingLineupDTO?>?,
    val substitutes: List<SubstituteDTO?>?
){
    fun toHomeTeam() : HomeTeam{
        return HomeTeam(
            coaches = coaches?.map { it?.toCoach() },
            missing_players = missing_players,
            starting_lineups = starting_lineups?.map { it?.toStartingLineup() },
            substitutes = substitutes?.map { it?.toSubstitute() }
        )
    }
}

data class CoachDTO(
    val coach: String?,
    val coach_country: Any?
){
    fun toCoach() : Coach{
        return Coach(
            coach = coach,
            coach_country = coach_country
        )
    }
}

data class StartingLineupDTO(
    val info_time: String?,
    val player: String?,
    val player_country: Any?,
    val player_key: Long?,
    val player_number: Int?,
    val player_position: Int?
){
    fun toStartingLineup() : StartingLineup{
        return StartingLineup(
            info_time = info_time,
            player = player,
            player_country = player_country,
            player_key = player_key,
            player_number = player_number,
            player_position = player_position
        )
    }
}

data class AwayTeamDTO(
    val coaches: List<CoachDTO?>?,
    val missing_players: List<Any?>?,
    val starting_lineups: List<StartingLineupDTO?>?,
    val substitutes: List<SubstituteDTO?>?
){
    fun toAwayTeam() : AwayTeam{
        return AwayTeam(
            coaches = coaches?.map { it?.toCoach() },
            missing_players = missing_players,
            starting_lineups = starting_lineups?.map { it?.toStartingLineup() },
            substitutes = substitutes?.map { it?.toSubstitute() }
        )
    }
}

data class SubstituteDTO(
    val info_time: String?,
    val player: String?,
    val player_country: Any?,
    val player_key: Long?,
    val player_number: Int?,
    val player_position: Int?
){
    fun toSubstitute() : Substitute{
        return Substitute(
            info_time = info_time,
            player = player,
            player_country = player_country,
            player_key = player_key,
            player_number = player_number,
            player_position = player_position
        )
    }
}

data class StatisticDTO(
    val away: String?,
    val home: String?,
    val type: String?
){
    fun toStatistic() : Statistic{
        return Statistic(
            away = away,
            home = home,
            type = type
        )
    }
}

data class SubstituteXXDTO(
    val away_assist: Any?,
//    val away_scorer: AwayScorerDTO,
    val home_assist: Any?,
//    val home_scorer: HomeScorerDTO,
    val info: Any?,
    val info_time: String?,
    val score: String?,
    val time: String?
){
    fun toSubstituteXX() : SubstituteXX{
        return SubstituteXX(
            away_assist = away_assist,
//            away_scorer = away_scorer.toAwayScorer(),
            home_assist = home_assist,
//            home_scorer = home_scorer.toHomeScorer(),
            info = info,
            info_time = info_time,
            score = score,
            time = time
        )
    }
}

data class AwayScorerDTO(
    val `in`: String?,
    val in_id: Long?,
    val `out`: String?,
    val out_id: Long?
){
    fun toAwayScorer() : AwayScorer{
        return AwayScorer(
            `in` = `in`,
            in_id = in_id,
            out = out,
            out_id = out_id
        )
    }
}

data class HomeScorerDTO(
    val `in`: String?,
    val in_id: Int?,
    val `out`: String?,
    val out_id: Long?
){
    fun toHomeScorer() : HomeScorer{
        return HomeScorer(
            `in` = `in`,
            in_id = in_id,
            out = out,
            out_id = out_id
        )
    }
}