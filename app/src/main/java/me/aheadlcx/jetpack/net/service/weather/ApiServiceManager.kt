package me.aheadlcx.jetpack.net.service.weather


import com.ihsanbal.logging.LoggingInterceptor
import me.aheadlcx.jetpack.net.flow.FlowCallAdapterFactory
import me.aheadlcx.jetpack.net.service.weather.Interceptor.CommonParamsInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiServiceManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.seniverse.com/v3/")
        .addCallAdapterFactory(FlowCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor.Builder().build())
//                .addInterceptor(CommonParamsInterceptor())
//                .addInterceptor(BaseUrlInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .build()
        )
        .build()
    val weatherApiService: WeatherApiService = retrofit.create(WeatherApiService::class.java)
}