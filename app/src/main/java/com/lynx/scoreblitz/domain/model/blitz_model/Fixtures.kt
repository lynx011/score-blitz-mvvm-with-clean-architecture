package com.lynx.scoreblitz.domain.model.blitz_model

data class Fixtures(
    val `data`: List<FixtureData?>?,
    val pagination: Pagination?,
    val timezone: String?
)

data class FixtureData(
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
    val scores: List<Score?>?,
    val season: Season?,
    val season_id: Int?,
    val sport_id: Int?,
    val stage_id: Int?,
    val starting_at: String?,
    val starting_at_timestamp: Int?,
    val state_id: Int?,
    val venue_id: Int?
)

data class League(
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
)

data class Participant(
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
)

data class Meta(
    val location: String?,
    val position: Int?,
    val winner: Boolean?
)

data class Score(
    val description: String?,
    val fixture_id: Int?,
    val id: Int?,
    val participant_id: Int?,
    val score: ScoreX?,
    val type_id: Int?
)

data class ScoreX(
    val goals: Int?,
    val participant: String?
)

data class Season(
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
)

data class Odd(
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
)

data class Pagination(
    val count: Int,
    val current_page: Int,
    val has_more: Boolean,
    val next_page: String,
    val per_page: Int
)