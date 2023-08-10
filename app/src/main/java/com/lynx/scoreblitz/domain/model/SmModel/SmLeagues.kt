package com.lynx.scoreblitz.domain.model.SmModel

data class SmLeagues(
    val `data`: List<SmLeagueList>
)

data class SmLeagueList(
    val active: Boolean,
    val category: Int,
    val country_id: Int,
    val has_jerseys: Boolean,
    val id: Int,
    val image_path: String,
    val last_played_at: String,
    val name: String,
    val short_code: String,
    val sport_id: Int,
    val sub_type: String,
    val type: String
)