package com.lynx.scoreblitz.domain.use_cases

import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.H2HResponse
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.domain.repository.ScoreRepository
import com.lynx.scoreblitz.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FixtureDetailsUseCase @Inject constructor(private val repository: ScoreRepository) {

    suspend operator fun invoke(met: String, firstTeamId: Int, secondTeamId: Int, apiKey: String) : Flow<ApiResponse<H2HResponse?>> {
        return if (met.isEmpty() && firstTeamId.equals(null) && secondTeamId.equals(null) && apiKey.isEmpty()){
            flow {  }
        }else{
            repository.getH2H(met, firstTeamId, secondTeamId, apiKey)
        }
    }
}