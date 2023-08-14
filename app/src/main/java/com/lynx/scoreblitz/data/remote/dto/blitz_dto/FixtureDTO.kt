package com.lynx.scoreblitz.data.remote.dto.blitz_dto

import com.lynx.scoreblitz.domain.model.blitz_model.FixtureData
import com.lynx.scoreblitz.domain.model.blitz_model.League
import com.lynx.scoreblitz.domain.model.blitz_model.Meta
import com.lynx.scoreblitz.domain.model.blitz_model.Odd
import com.lynx.scoreblitz.domain.model.blitz_model.Pagination
import com.lynx.scoreblitz.domain.model.blitz_model.Participant
import com.lynx.scoreblitz.domain.model.blitz_model.Score
import com.lynx.scoreblitz.domain.model.blitz_model.ScoreX
import com.lynx.scoreblitz.domain.model.blitz_model.Season

data class FixtureDTO(
    val `data`: List<FixtureDataDTO?>?,
    val pagination: PaginationDTO?,
    val timezone: String?
)

data class FixtureDataDTO(
    val aggregate_id: Int?,
    val details: Any?,
    val group_id: Int?,
    val has_odds: Boolean?,
    val id: Int?,
    val league: League?,
    val league_id: Int?,
    val leg: String?,
    val length: Int?,
    val name: String?,
    val odds: List<Odd?>?,
    val participants: List<Participant?>?,
    val placeholder: Boolean?,
    val result_info: String?,
    val round_id: Int?,
    val scores: List<ScoreDTO?>?,
    val season: Season?,
    val season_id: Int?,
    val sport_id: Int?,
    val stage_id: Int?,
    val starting_at: String?,
    val starting_at_timestamp: Int?,
    val state_id: Int?,
    val venue_id: Int?
){
    fun toFixtureData(): FixtureData{
        return FixtureData(
            aggregate_id = aggregate_id,
            details = details,
            group_id = group_id,
            has_odds = has_odds,
            id = id,
            league = league,
            league_id = league_id,
            leg = leg,
            length = length,
            name = name,
            odds = odds,
            participants = participants,
            placeholder = placeholder,
            result_info = result_info,
            round_id = round_id,
            scores = scores?.map { it?.toScore() },
            season = season,
            season_id = season_id,
            sport_id = sport_id,
            stage_id = stage_id,
            starting_at = starting_at,
            starting_at_timestamp = starting_at_timestamp,
            state_id = state_id,
            venue_id =venue_id
        )
    }
}

data class LeagueDTO(
    val active: Boolean?,
    val category: Int?,
    val country_id: Int?,
    val has_jerseys: Boolean?,
    val id: Int?,
    val image_path: String?,
    val last_played_at: String?,
    val name: String?,
    val short_code: String?,
    val sport_id: Int?,
    val sub_type: String?,
    val type: String?
){
    fun toLeague(): League {
        return League(
            active = active,
            category = category,
            country_id = country_id,
            has_jerseys = has_jerseys,
            id = id,
            image_path = image_path,
            last_played_at = last_played_at,
            name = name,
            short_code = short_code,
            sport_id = sport_id,
            sub_type = sub_type,
            type = type
        )
    }
}

data class ParticipantDTO(
    val country_id: Int?,
    val founded: Int?,
    val gender: String?,
    val id: Int?,
    val image_path: String?,
    val last_played_at: String?,
    val meta: Meta?,
    val name: String?,
    val placeholder: Boolean?,
    val short_code: String?,
    val sport_id: Int?,
    val type: String?,
    val venue_id: Int?
){
    fun toParticipant(): Participant {
        return Participant(
            country_id = country_id,
            founded = founded,
            gender = gender,
            id = id,
            image_path = image_path,
            last_played_at = last_played_at,
            meta = meta,
            name = name,
            placeholder = placeholder,
            short_code = short_code,
            sport_id = sport_id,
            type = type,
            venue_id = venue_id
        )
    }
}

data class ScoreDTO(
    val description: String?,
    val fixture_id: Int?,
    val id: Int?,
    val participant_id: Int?,
    val score: ScoreXDTO?,
    val type_id: Int?
){
    fun toScore(): Score{
        return Score(
            description = description,
            fixture_id = fixture_id,
            id = id,
            participant_id = participant_id,
            score = score?.toScoreX(),
            type_id = type_id
        )
    }
}

data class ScoreXDTO(
    val goals: Int?,
    val participant: String?
){
    fun toScoreX(): ScoreX {
        return ScoreX(
            goals = goals,
            participant = participant
        )
    }
}

data class MetaDTO(
    val location: String?,
    val position: Int?,
    val winner: Boolean?
){
    fun toMeta(): Meta {
        return Meta(
            location = location,
            position = position,
            winner = winner
        )
    }
}

data class SeasonDTO(
    val ending_at: String?,
    val finished: Boolean?,
    val games_in_current_week: Boolean?,
    val id: Int?,
    val is_current: Boolean?,
    val league_id: Int?,
    val name: String?,
    val pending: Boolean?,
    val sport_id: Int?,
    val standings_recalculated_at: String?,
    val starting_at: String?,
    val tie_breaker_rule_id: Int?
){
    fun toSeason(): Season {
        return Season(
            ending_at = ending_at,
            finished = finished,
            games_in_current_week = games_in_current_week,
            id = id,
            is_current = is_current,
            league_id = league_id,
            name = name,
            pending = pending,
            sport_id = sport_id,
            standings_recalculated_at = standings_recalculated_at,
            starting_at = starting_at,
            tie_breaker_rule_id = tie_breaker_rule_id
        )
    }
}

data class OddDTO(
    val american: String?,
    val bookmaker_id: Int?,
    val created_at: String?,
    val dp3: String?,
    val fixture_id: Int?,
    val fractional: String?,
    val handicap: String?,
    val id: Int?,
    val label: String?,
    val latest_bookmaker_update: String?,
    val market_description: String?,
    val market_id: Int?,
    val name: String?,
    val original_label: Any?,
    val participants: String?,
    val probability: String?,
    val sort_order: Any?,
    val stopped: Boolean?,
    val total: Any?,
    val updated_at: String?,
    val value: String?,
    val winning: Boolean?
){
    fun toOdd(): Odd {
        return Odd(
            american = american,
            bookmaker_id = bookmaker_id,
            created_at = created_at,
            dp3 = dp3,
            fixture_id = fixture_id,
            fractional = fractional,
            handicap = handicap,
            id = id,
            label = label,
            latest_bookmaker_update = latest_bookmaker_update,
            market_description = market_description,
            market_id = market_id,
            name = name,
            original_label = original_label,
            participants = participants,
            probability = probability,
            sort_order = sort_order,
            stopped = stopped,
            total = total,
            updated_at = updated_at,
            value = value,
            winning = winning
        )
    }
}

data class PaginationDTO(
    val count: Int,
    val current_page: Int,
    val has_more: Boolean,
    val next_page: String,
    val per_page: Int
){
    fun toPagination(): Pagination {
        return Pagination(
            count = count,
            current_page = current_page,
            has_more = has_more,
            next_page = next_page,
            per_page = per_page
        )
    }
}