package com.lynx.scoreblitz.data.remote.api_service

import com.lynx.scoreblitz.data.remote.dto.FixturesDTO
import com.lynx.scoreblitz.data.remote.dto.H2HDTO
import com.lynx.scoreblitz.data.remote.dto.LeaguesDTO
import com.lynx.scoreblitz.data.remote.dto.StandingsDTO
import com.lynx.scoreblitz.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ScoreApiService {
    @GET(Constants.FOOTBALL)
    suspend fun getLeagues(@Query("met") met: String, @Query("APIkey") apiKey: String): LeaguesDTO

    @GET(Constants.FOOTBALL)
    suspend fun getFixtures(
        @Query("met") met: String,
        @Query("leagueId") leagueId: Int,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("APIkey") apiKey: String
    ) : FixturesDTO?

    @GET(Constants.FOOTBALL)
    suspend fun getH2H(
        @Query("met") met: String,
        @Query("firstTeamId") firstTeamId: Int,
        @Query("secondTeamId") secondTeamId: Int,
        @Query("APIkey") apiKey: String
    ) : H2HDTO

    @GET(Constants.FOOTBALL)
    suspend fun getStandings(
        @Query("met") met: String,
        @Query("leagueId") leagueId: Int,
        @Query("APIkey") apiKey: String
    ) : StandingsDTO

}