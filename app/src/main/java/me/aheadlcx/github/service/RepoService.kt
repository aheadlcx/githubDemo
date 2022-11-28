package me.aheadlcx.github.service

import com.shuyu.github.kotlin.model.bean.Repository
import kotlinx.coroutines.flow.Flow
import me.aheadlcx.github.config.AppConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/28 6:47 下午
 */
interface RepoService {

    @GET("users/{user}/repos")
    fun getUserRepository100StatusDao(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "pushed",
        @Query("per_page") per_page: Int = 100
    ): Flow<ArrayList<Repository>>


    @GET("users/{user}/starred")
    fun getStarredRepos(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "updated",
        @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Flow<ArrayList<Repository>>
}