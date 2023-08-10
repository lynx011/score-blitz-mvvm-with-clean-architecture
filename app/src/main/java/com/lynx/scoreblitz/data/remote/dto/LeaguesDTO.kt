package com.lynx.scoreblitz.data.remote.dto

import com.lynx.scoreblitz.domain.model.Leagues

data class LeaguesDTO(
    val result: List<Result>,
    val success: Int
)

data class Result(
    val country_key: Int?,
    val country_logo: String?,
    val country_name: String?,
    val league_key: Int?,
    val league_logo: String?,
    val league_name: String?
){
    fun toLeagues() : Leagues {
        return Leagues(
            country_key = country_key,
            country_logo = country_logo,
            country_name = country_name,
            league_key = league_key,
            league_logo = league_logo,
            league_name = league_name
        )
    }
}