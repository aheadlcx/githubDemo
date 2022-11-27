package me.aheadlcx.github.module.dynamic

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import me.aheadlcx.github.common.net.GsonUtils
import me.aheadlcx.github.module.base.BaseViewModel
import me.aheadlcx.github.repository.UserRepository

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 11:12 下午
 */
class DynamicViewModel:BaseViewModel() {

    companion object{
        private const val TAG = "DynamicViewModel"
    }

    private val repo: UserRepository = UserRepository()
    fun getReceivedEvent(page:Int = 1){
        Log.i(TAG, "getReceivedEvent:begin")
        viewModelScope.launch {
            repo.getReceivedEvent(page)
                .catch {
                    Log.i(TAG, "getReceivedEvent:error")
                }.onCompletion {  cause ->
                    Log.i(TAG, "getReceivedEvent: onCompletion" + (cause?.message?:"cause.is.null"))

                }
                .collect{
                    Log.i(TAG, "getReceivedEvent:.success" )
                    Log.i(TAG, "getReceivedEvent:.success" + GsonUtils.toJsonString(it))
                }

        }
    }

    fun getFollowers(page:Int = 1){
        Log.i(TAG, "getFollowers:begin")
        viewModelScope.launch {
            repo.getFollowers(page)
                .catch {
                    Log.i(TAG, "getFollowers:error")
                }.onCompletion {  cause ->
                    Log.i(TAG, "getFollowers: onCompletion" + (cause?.message?:"cause.is.null"))

                }
                .collect{
                    Log.i(TAG, "getFollowers:.success" )
                    Log.i(TAG, "getFollowers:.success" + GsonUtils.toJsonString(it))
                }

        }
    }

}