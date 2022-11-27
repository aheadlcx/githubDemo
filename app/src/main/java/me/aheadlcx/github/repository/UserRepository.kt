package me.aheadlcx.github.repository

import android.util.Log
import com.shuyu.github.kotlin.model.bean.Event
import com.shuyu.github.kotlin.model.bean.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import me.aheadlcx.github.api.GithubApiServiceManager
import me.aheadlcx.github.module.dynamic.DynamicViewModel
import java.util.ArrayList

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 11:02 下午
 */
class UserRepository {
    companion object {
        private const val TAG = "DynamicViewModel"
    }

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

}