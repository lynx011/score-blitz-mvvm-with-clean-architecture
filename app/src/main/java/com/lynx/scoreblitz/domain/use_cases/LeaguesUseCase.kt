package com.lynx.scoreblitz.domain.use_cases

import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.domain.repository.ScoreRepository
import com.lynx.scoreblitz.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class LeaguesUseCase @Inject constructor(private val repository: ScoreRepository) {
    suspend operator fun invoke(met: String, apiKey: String) : Flow<ApiResponse<List<Leagues>>> {
        return if (met.isEmpty()){
            flow {  }
        }else{
            repository.getLeagues(met, apiKey)
        }
    }

    suspend operator fun invoke(met: String, leagueId: Int, from: String, to: String, apiKey: String) : Flow<ApiResponse<List<FixtureResult?>?>> {
        return if (met.isEmpty() && leagueId.equals(null) && from.isEmpty() && to.isEmpty() && apiKey.isEmpty()){
            flow {  }
        }else{
            repository.getFixtures(met, leagueId, from, to, apiKey)
        }
    }
}