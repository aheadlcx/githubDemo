package me.aheadlcx.github.repository

import android.util.Log
import com.shuyu.github.kotlin.model.bean.Event
import com.shuyu.github.kotlin.model.bean.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import me.aheadlcx.github.api.GithubApiServiceManager
import me.aheadlcx.github.common.net.GsonUtils
import me.aheadlcx.github.config.AppConfig
import me.aheadlcx.github.module.dynamic.DynamicViewModel
import me.aheadlcx.github.utils.GSYPreference
import java.util.ArrayList
import kotlinx.coroutines.flow.flatMap as flowFlatMap

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 11:02 下午
 */
class UserRepository {
    companion object {
        private const val TAG = "UserRepository"
    }

    /**
     * 登录用户的 SharedPreferences 委托
     */
    private var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO, "")

    suspend fun getReceivedEvent(page: Int = 1): Flow<ArrayList<Event>> {
        val username = "aheadlcx"
        val flow = GithubApiServiceManager.userService.getNewsEvent(true, username, page)
            .flowOn(Dispatchers.IO)
            .map {
                Log.i(TAG, "getReceivedEvent:map")
                it
            }
        return flow
    }

    suspend fun getFollowers(page: Int = 1): Flow<ArrayList<User>> {
        val username = "aheadlcx"
        val flow = GithubApiServiceManager.userService.getFollowers(true, username, page)
            .flowOn(Dispatchers.IO)
            .map {
                Log.i(TAG, "getFollowers:map")
                it
            }
        return flow
    }

    suspend fun getPersonUserInfo(userName: String? = null): Flow<User> {
        val isLoginUser = userName == null
        //根据是否有用户名，获取第三方用户数据或者当前用户数据
        val userFlow = if (isLoginUser) {
            val personInfoFlow = GithubApiServiceManager.userService.getPersonInfo(true)
            personInfoFlow
        } else {
            val userFlow = GithubApiServiceManager.userService.getUser(true, userName!!)
            userFlow
        }
        return doUserInfoFlat(userFlow, isLoginUser)
    }

    private suspend fun doUserInfoFlat(userFlow: Flow<User>, loginUser: Boolean): Flow<User> {
        val userFlow = userFlow
            .flowOn(Dispatchers.IO)
            .catch { cause ->
                Log.i(TAG, "doUserInfoFlat.errorMsg: ${cause.message}")
            }
            .map {
                if (loginUser) {
                    userInfoStorage = GsonUtils.toJsonString(it)
                }
                it
            }
        return userFlow
    }


}