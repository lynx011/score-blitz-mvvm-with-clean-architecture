package com.lynx.scoreblitz.data.data_sources.api_service

import com.lynx.scoreblitz.data.data_sources.dto.FixtureResultDTO
import com.lynx.scoreblitz.data.data_sources.dto.FixturesDTO
import com.lynx.scoreblitz.data.data_sources.dto.LeaguesDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ScoreApiService {
    @GET("football")
    suspend fun getLeagues(@Query("met") met: String, @Query("APIkey") apiKey: String): LeaguesDTO

    @GET("football")
    suspend fun getFixtures(
        @Query("met") met: String,
        @Query("leagueId") leagueId: Int,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("APIkey") apiKey: String
    ) : FixturesDTO
}