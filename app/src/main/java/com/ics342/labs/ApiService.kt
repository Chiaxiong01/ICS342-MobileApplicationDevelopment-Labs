package com.ics342.labs

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/path/to/data")
    suspend fun getWeatherData(
        @Query("zip") zip: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "75d539fd7c64e5b40c5adaf045d766d4"
    ): WeatherData

    suspend fun getForecastData(
        @Query("zip") zip: String,
        @Query("cnt") count: Int = 16,
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "75d539fd7c64e5b40c5adaf045d766d4"
    ): List<Forecast>
}