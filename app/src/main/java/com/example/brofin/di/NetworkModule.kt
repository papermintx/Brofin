package com.example.brofin.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.brofin.data.remote.service.ApiService
import com.example.brofin.data.remote.AuthInterceptor
import com.example.brofin.data.remote.service.PredictApiService
import com.example.brofin.data.remote.service.RecommendationApiService
import com.example.brofin.data.repository.RemoteDataRepositoryImpl
import com.example.brofin.domain.repository.RemoteDataRepository
import com.example.brofin.utils.BaseUrl
import com.example.brofin.utils.Constant
import com.example.brofin.utils.PredictUrl
import com.example.brofin.utils.RecommendationUrl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideAuthInterceptor(dataStore: DataStore<Preferences>): AuthInterceptor {
        return AuthInterceptor(dataStore)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshit(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrlRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @PredictUrl
    fun providePredictUrlRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.PREDICT_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @RecommendationUrl
    fun provideRecommendationUrlRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.RECOMENDATION_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrlApiService(@BaseUrl retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    @RecommendationUrl
    fun provideRecommendationUrlApiService(@RecommendationUrl retrofit: Retrofit): RecommendationApiService {
        return retrofit.create(RecommendationApiService::class.java)
    }

    @Provides
    @Singleton
    @PredictUrl
    fun providePredictUrlApiService(@PredictUrl retrofit: Retrofit): PredictApiService {
        return retrofit.create(PredictApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataRepository(
        @BaseUrl apiService: ApiService,
        @PredictUrl predictService: PredictApiService,
        @RecommendationUrl recommendationService: RecommendationApiService
    ): RemoteDataRepository {
        return RemoteDataRepositoryImpl(
            apiService = apiService,
            predictService = predictService,
            recommendationService = recommendationService
        )
    }

}