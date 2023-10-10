package com.lynx.scoreblitz.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.lynx.scoreblitz.data.remote.api_service.ScoreApiService
import com.lynx.scoreblitz.data.repository_impl.ScoreRepositoryImpl
import com.lynx.scoreblitz.domain.repository.ScoreRepository
import com.lynx.scoreblitz.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    @Named("Collector")
    fun provideCollector(@ApplicationContext context: Context) : ChuckerCollector = ChuckerCollector(
        context = context,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_DAY
    )

    @Provides
    @Singleton
    @Named("Chucker")
    fun provideChucker(@ApplicationContext context: Context, @Named("Collector") collector: ChuckerCollector) : ChuckerInterceptor = ChuckerInterceptor.Builder(context)
        .collector(collector)
        .maxContentLength(250000L)
        .alwaysReadResponseBody(true)
        .build()

    @Provides
    @Singleton
    @Named("ScoreService")
    fun provideHttpService(@Named("Chucker") chuckerInterceptor: ChuckerInterceptor): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .build()
            chain.proceed(newRequest)
        }
        .addInterceptor(chuckerInterceptor)
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()

    @Provides
    @Singleton
    @Named("ScoreRetrofit")
    fun provideRetrofit(@Named("ScoreService") okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideScoreApiService(@Named("ScoreRetrofit") retrofit: Retrofit): ScoreApiService =
        retrofit.create(ScoreApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(scoreApiService: ScoreApiService) : ScoreRepository {
        return ScoreRepositoryImpl(scoreApiService)
    }

}