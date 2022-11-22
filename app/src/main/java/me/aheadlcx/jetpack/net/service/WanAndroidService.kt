package me.aheadlcx.jetpack.net.service

import kotlinx.coroutines.flow.Flow
import me.aheadlcx.github.api.adapter.ApiResult
import me.aheadlcx.jetpack.net.service.bean.BaseBizResponse
import me.aheadlcx.wan.net.bean.Banner
import retrofit2.http.GET

interface WanAndroidService {
    @GET("banner/json")
    fun getBanner(): Flow<BaseBizResponse<List<Banner>>>
}