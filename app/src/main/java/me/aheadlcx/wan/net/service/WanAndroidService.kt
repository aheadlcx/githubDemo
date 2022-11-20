package me.aheadlcx.wan.net.service

import me.aheadlcx.github.api.adapter.ApiResult
import me.aheadlcx.wan.net.bean.Banner
import me.aheadlcx.wan.net.bean.Banner2
import retrofit2.http.GET

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/20 8:20 下午
 */
interface WanAndroidService {
    @GET("banner/json")
    suspend fun banner(): ApiResult<List<Banner>>

    @GET("banner/json")
    suspend fun banner2(): Banner2
}