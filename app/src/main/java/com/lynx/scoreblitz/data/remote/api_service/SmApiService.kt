package com.lynx.scoreblitz.data.remote.api_service

import SmFixturesDTO
import SmLeaguesDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface SmApiService {

    @GET("leagues/{leagueId}")
    suspend fun getSmLeague(@Path("leagueId") leagueId: Int) : SmLeaguesDTO

    @GET("fixtures/date/{date}")
    suspend fun getSmFixture(@Path("date") date: String) : SmFixturesDTO

}