package com.lynx.scoreblitz.domain.model.SmModel

data class SmFixtures(
    val `data`: List<SmFixtureList>
)

data class SmFixtureList(
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
)