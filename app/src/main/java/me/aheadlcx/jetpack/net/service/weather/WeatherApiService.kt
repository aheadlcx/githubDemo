package me.aheadlcx.jetpack.net.service.weather

import com.example.sharp_retrofit.api_service.WeatherInfo
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather/now.json")
    fun getWeatherInfoNow(@Query("location") location: String): Flow<WeatherInfo>
}