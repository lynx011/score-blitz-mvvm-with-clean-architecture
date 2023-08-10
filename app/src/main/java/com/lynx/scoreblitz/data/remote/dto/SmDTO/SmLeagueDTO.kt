package com.lynx.scoreblitz.data.remote.dto.SmDTO

import com.lynx.scoreblitz.domain.model.SmModel.SmLeague
import com.lynx.scoreblitz.domain.model.SmModel.SmLeagues

data class SmLeagueDTO(
    val data: List<SmLeaguesList>,
    val rate_limit: LeagueRateLimit,
    val subscription: List<LeagueSubscription>,
    val timezone: String
){
    fun toSmLeague(): SmLeague {
        return SmLeague(
            data = data.map { it.toSmLeagues() },
            timezone = timezone
        )
    }
}

data class SmLeaguesList(
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
){
    fun toSmLeagues() : SmLeagues {
        return SmLeagues(
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

data class LeagueSubscription(
    val add_ons: List<Any>,
    val meta: LeagueMeta,
    val plans: List<LeaguePlan>,
    val widgets: List<Any>
)

data class LeagueMeta(
    val ends_at: String,
    val trial_ends_at: Any
)

data class LeaguePlan(
    val category: String,
    val plan: String,
    val sport: String
)

data class LeagueRateLimit(
    val remaining: Int,
    val requested_entity: String,
    val resets_in_seconds: Int
)