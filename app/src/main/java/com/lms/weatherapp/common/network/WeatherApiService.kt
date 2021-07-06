package com.lms.weatherapp.network

import com.lms.weatherapp.model.location.GeopositionResponse
import com.lms.weatherapp.model.weather.CurrentConditionsResponse
import com.lms.weatherapp.model.weather.ForecastResponse
import com.lms.weatherapp.model.weather.HourlyResponse
import com.lms.wheatherapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherApiService {

    @GET("locations/v1/cities/geoposition/search")
    fun getLocationKeyBygeoposition(
        @Query("q") q: String,
        @Query("language") language: String = "es-ES"
    ): Call<GeopositionResponse>

    @GET("currentconditions/v1/{locationKey}")
    fun getCurrentConditionsByLocationKey(
        @Path("locationKey") locationKey : String,
        @Query("language") language: String = "es-ES"
    ): Call<List<CurrentConditionsResponse>>

    @GET("forecasts/v1/daily/5day/{locationKey}")
    fun get5DaysForecast(
        @Path("locationKey") locationKey : String,
        @Query("language") language: String = "es-ES"
    ): Call<ForecastResponse>

    @GET("forecasts/v1/hourly/12hour/{locationKey}")
    fun getHourly12hours(
        @Path("locationKey") locationKey: String,
        @Query("language") language: String = "es-ES"
    ): Call<List<HourlyResponse>>


    companion object Factory {
        fun create() : WeatherApiService {
            val client = OkHttpClient.Builder().apply {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(interceptor)
                    addInterceptor(Interceptor { chain ->
                        val original = chain.request()
                        val originalHttpUrl = original.url
                        val url = originalHttpUrl.newBuilder()
                            .addQueryParameter("apikey", BuildConfig.PUBLIC_KEY)
                            .build()

                        // Request customization: add request headers
                        val requestBuilder = original.newBuilder()
                            .url(url)
                        val request = requestBuilder.build()
                        chain.proceed(request)
                    })

            }.build()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://dataservice.accuweather.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(WeatherApiService::class.java)
        }
    }
}

