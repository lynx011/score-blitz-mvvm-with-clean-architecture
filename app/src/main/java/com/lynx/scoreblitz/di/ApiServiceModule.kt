package com.lynx.scoreblitz.di

import com.lynx.scoreblitz.data.data_sources.api_service.ScoreApiService
import com.lynx.scoreblitz.data.repository_impl.ScoreRepositoryImpl
import com.lynx.scoreblitz.domain.repository.ScoreRepository
import com.lynx.scoreblitz.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    @Named("ScoreService")
    fun provideHttpService(): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(@Named("ScoreService") okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGameApiService(retrofit: Retrofit): ScoreApiService =
        retrofit.create(ScoreApiService::class.java)

    @Provides
    @Singleton
    fun provideGameRepository(apiService: ScoreApiService): ScoreRepository {
        return ScoreRepositoryImpl(apiService)
    }
}