package com.lynx.scoreblitz.data.remote.dto.SmDTO

import com.lynx.scoreblitz.domain.model.SmModel.SmFixture
import com.lynx.scoreblitz.domain.model.SmModel.SmFixtures

data class SmFixtureDTO(
    val data: List<SmFixturesList>,
    val rate_limit: FixtureRateLimit,
    val subscription: List<FixtureSubscription>,
    val timezone: String
){
    fun toSmFixtureList() : SmFixture {
        return SmFixture(
            data = data.map { it.toSmFixtures() },
            timezone = timezone
        )
    }
}

data class SmFixturesList(
    val aggregate_id: Any,
    val details: Any,
    val group_id: Any,
    val has_odds: Boolean,
    val id: Int,
    val league_id: Int,
    val leg: String,
    val length: Int,
    val name: String,
    val placeholder: Boolean,
    val result_info: String,
    val round_id: Int,
    val season_id: Int,
    val sport_id: Int,
    val stage_id: Int,
    val starting_at: String,
    val starting_at_timestamp: Int,
    val state_id: Int,
    val venue_id: Int
){
    fun toSmFixtures() : SmFixtures{
        return SmFixtures(
            aggregate_id = aggregate_id,
            details = details,
            group_id = group_id,
            has_odds = has_odds,
            id = id,
            league_id = league_id,
            leg = leg,
            length = length,
            name = name,
            placeholder = placeholder,
            result_info = result_info,
            round_id = round_id,
            season_id = season_id,
            sport_id = sport_id,
            stage_id = stage_id,
            starting_at = starting_at,
            starting_at_timestamp = starting_at_timestamp,
            state_id = state_id,
            venue_id = venue_id
        )
    }
}

data class FixtureSubscription(
    val add_ons: List<Any>,
    val meta: FixtureMeta,
    val plans: List<FixturePlan>,
    val widgets: List<Any>
)

data class FixtureMeta(
    val ends_at: String,
    val trial_ends_at: Any
)

data class FixturePlan(
    val category: String,
    val plan: String,
    val sport: String
)

data class FixtureRateLimit(
    val remaining: Int,
    val requested_entity: String,
    val resets_in_seconds: Int
)