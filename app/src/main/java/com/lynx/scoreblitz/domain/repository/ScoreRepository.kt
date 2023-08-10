package com.lynx.scoreblitz.domain.repository

import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.H2HResponse
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.domain.model.SmModel.SmFixtureList
import com.lynx.scoreblitz.domain.model.SmModel.SmLeagueList
import com.lynx.scoreblitz.domain.model.StandingList
import com.lynx.scoreblitz.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ScoreRepository {

    suspend fun getLeagues(met: String, apiKey: String): Flow<ApiResponse<List<Leagues>>>

    suspend fun getFixtures(
        met: String,
        leagueId: Int,
        from: String,
        to: String,
        apiKey: String
    ): Flow<ApiResponse<List<FixtureResult?>?>>

    suspend fun getH2H(
        met: String,
        firstTeamId: Int,
        secondTeamId: Int,
        apiKey: String
    ): Flow<ApiResponse<H2HResponse?>>

    suspend fun getStandings(
        met: String,
        leagueId: Int,
        apiKey: String
    ) : Flow<ApiResponse<StandingList>>

    suspend fun getSmLeagues(
        leagueId: Int
    ) : Flow<ApiResponse<List<SmLeagueList?>?>>

    suspend fun getSmFixtures(
        date: String
    ) : Flow<ApiResponse<List<SmFixtureList?>?>>
}