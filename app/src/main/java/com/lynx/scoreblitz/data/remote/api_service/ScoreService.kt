package com.lynx.scoreblitz.data.remote.api_service

import com.lynx.scoreblitz.data.remote.dto.blitz_dto.FixtureDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScoreService {

    @GET("fixtures/date/{date}")
    suspend fun getScores(
        @Path("date") date: String,
        @Query("include") include: String,
        @Query("filter") filter: String,
        @Query("page") page: Int
    ): FixtureDTO
}