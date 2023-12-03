package com.kyawzinlinn.core_network.di

import com.kyawzinlinn.core_network.repository.WeatherNetworkDataSource
import com.kyawzinlinn.core_network.repository.WeatherRepository
import com.kyawzinlinn.core_network.retrofit.AuthInterceptor
import com.kyawzinlinn.core_network.retrofit.BuildConfig
import com.kyawzinlinn.core_network.retrofit.RetrofitNetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun providesRetrofitNetworkApi() : RetrofitNetworkApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RetrofitNetworkApi::class.java)
    }

    @Provides
    fun providesWeatherNetworkDataSource(retrofitNetworkApi: RetrofitNetworkApi): WeatherRepository {
        return WeatherNetworkDataSource(retrofitNetworkApi)
    }
}