package com.lynx.scoreblitz.domain.repository

import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ScoreRepository {
    suspend fun getLeagues(met: String, apiKey: String) : Flow<ApiResponse<List<Leagues>>>

    suspend fun getFixtures(met: String, leagueId: Int, from: String, to: String, apiKey: String) : Flow<ApiResponse<List<FixtureResult?>?>>
}