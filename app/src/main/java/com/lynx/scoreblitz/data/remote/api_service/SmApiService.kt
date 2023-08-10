package com.lynx.scoreblitz.data.remote.api_service

import com.lynx.scoreblitz.data.remote.dto.SmDTO.SmFixtureDTO
import com.lynx.scoreblitz.data.remote.dto.SmDTO.SmLeagueDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface SmApiService {

    @GET("leagues/{leagueId}")
    suspend fun getSmLeague(@Path("leagueId") leagueId: Int) : SmLeagueDTO

    @GET("fixtures/date/{date}")
    suspend fun getSmFixture(@Path("date") date: String) : SmFixtureDTO

}