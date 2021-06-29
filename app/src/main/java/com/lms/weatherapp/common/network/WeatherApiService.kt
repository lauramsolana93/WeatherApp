package com.lms.weatherapp.network

import com.google.gson.GsonBuilder
import com.lms.weatherapp.model.location.GeopositionResponse
import com.lms.weatherapp.model.weather.CurrentConditionsResponse
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
        @Query("language") language: String = "es-ES",
        @Path("locationKey") locationKey : String
    ): List<CurrentConditionsResponse>

    companion object Factory {
        fun create() : WeatherApiService {
            val gson = GsonBuilder().create()

            val client = OkHttpClient.Builder().apply {
                //if(BuildConfig.DEBUG){
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(interceptor)
                    addInterceptor(Interceptor { chain ->
                        val original = chain.request()
                        val originalHttpUrl = original.url
                        val url = originalHttpUrl.newBuilder()
                            .addQueryParameter("key", "value")
                            .build()

                        // Request customization: add request headers
                        val requestBuilder = original.newBuilder()
                            .url(url)
                        val request = requestBuilder.build()
                        chain.proceed(request)
                    })
                //}
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

