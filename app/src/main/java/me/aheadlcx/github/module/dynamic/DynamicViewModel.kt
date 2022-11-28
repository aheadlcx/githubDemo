package me.aheadlcx.github.module.dynamic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shuyu.github.kotlin.model.conversion.EventConversion
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import me.aheadlcx.github.common.net.GsonUtils
import me.aheadlcx.github.model.ui.EventUIModel
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
    var page = 1

    private val repo: UserRepository = UserRepository()
    val eventUiModelLiveData = MutableLiveData<List<EventUIModel>>()
    fun getReceivedEvent(page:Int = 1){
        Log.i(TAG, "getReceivedEvent:begin")
        viewModelScope.launch {
            repo.getReceivedEvent(page)
                .catch {
                    Log.i(TAG, "getReceivedEvent:error")
                }.onCompletion {  cause ->
                    Log.i(TAG, "getReceivedEvent: onCompletion" + (cause?.message?:"cause.is.null"))

                }
                .map {
                    val eventUiList = ArrayList<EventUIModel>()
                    it.forEach {
                        eventUiList.add(EventConversion.eventToEventUIModel(it))
                    }
                    eventUiList
                }
                .collect{
                    Log.i(TAG, "getReceivedEvent:.success.size=${it.size}" )
                    eventUiModelLiveData.value = it
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
                    Log.i(TAG, "getFollowers:.success" + GsonUtils.toJsonString(it))
                }

        }
    }

}