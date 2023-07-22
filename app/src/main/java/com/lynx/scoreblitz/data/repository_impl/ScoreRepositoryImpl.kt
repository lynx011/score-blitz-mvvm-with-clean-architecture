package com.lynx.scoreblitz.data.repository_impl
import com.lynx.scoreblitz.data.data_sources.api_service.ScoreApiService
import com.lynx.scoreblitz.domain.model.FixtureResult
import com.lynx.scoreblitz.domain.model.Leagues
import com.lynx.scoreblitz.domain.repository.ScoreRepository
import com.lynx.scoreblitz.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ScoreRepositoryImpl @Inject constructor(private val apiService: ScoreApiService) : ScoreRepository {
    override suspend fun getLeagues(met: String, apiKey: String): Flow<ApiResponse<List<Leagues>>> = flow {
        try {
            emit(ApiResponse.Loading())
            val leagues = apiService.getLeagues(met, apiKey).result.map {
                it.toLeagues()
            }
            emit(ApiResponse.Success(data = leagues))
        }catch (e: HttpException){
            emit(ApiResponse.Error(e.localizedMessage?:"HttpException-An Expected Error Occurred!"))
        }catch (e: IOException){
            emit(ApiResponse.Error(e.localizedMessage?:"IOException-An Expected Error Occurred!"))
        }
    }

    override suspend fun getFixtures(
        met: String,
        leagueId: Int,
        from: String,
        to: String,
        apiKey: String
    ): Flow<ApiResponse<List<FixtureResult?>?>> = flow {
        try {
            emit(ApiResponse.Loading())
            val fixtures = apiService.getFixtures(met, leagueId, from, to, apiKey).result?.map {
                it.toFixtureResult()
            }
            emit(ApiResponse.Success(data = fixtures))
        }catch (e: HttpException){
            emit(ApiResponse.Error(message = e.localizedMessage?:"HttpException-An Expected Error Occurred!"))
        }catch (e: IOException){
            emit(ApiResponse.Error(message = e.localizedMessage?:"IOException-An Expected Error Occurred!"))
        }
    }
}