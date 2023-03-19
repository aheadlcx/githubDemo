package me.aheadlcx.github.test

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shuyu.github.kotlin.model.conversion.EventConversion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.aheadlcx.github.model.ui.EventUIModel
import me.aheadlcx.github.module.base.BaseViewModel
import me.aheadlcx.github.module.dynamic.DynamicViewModel
import me.aheadlcx.github.repository.UserRepository

/**
 * Description:
 * author: aheadlcx
 * Date:2022/12/22 4:15 下午
 */
class TestViewModel(val age : Int) : BaseViewModel() {


    companion object {
        private const val TAG = "TestViewModel"
    }

    private val repo: UserRepository = UserRepository()
    val eventUiModelLiveData = MutableLiveData<List<EventUIModel>>()

    var count = 1;
    var counFollower = 1;

    fun testZip() {
        viewModelScope.launch {
            val channel = Channel<String> {
            }
            channel.send("@2")
            channel.receive()
            val flowA0 = flow<String> {
                emit("aheadlcx")
            }
            val flowA = (1..5).asFlow()
            val flowB = flowOf("one", "two", "three", "four", "five")
            flowA.zip(flowB) { a, b -> "$a and $b" }
                .collect {
                    Log.i(TAG, "testZip: res=${it}")
                }
            flowA.combine(flowB){
                i, s -> i.toString() + s
            }.onEach {

            }.catch {

            }
                .flowOn(Dispatchers.IO)
//                .launchIn(viewModelScope)
                .collect{
                Log.i(TAG, "testZip: $it")
            }
            flowA.launchIn(viewModelScope)
        }
    }

    fun getData() {
        count++;
        counFollower++;
        Log.i(TAG, "getData: count=$count,counFollower=${counFollower}")
        viewModelScope.launch {
            val followersFlow = repo.getFollowers(1).map {
                if (counFollower % 2 == 1) {
                    throw Exception("counFollower % 2 == 1")
                }
                it
            }
            val receivedEventFlow = repo.getReceivedEvent().map {
                if (count % 2 == 0) {
                    throw Exception("count % 2 == 0")
                }
                it
            }
            receivedEventFlow.zip(followersFlow) { a, b ->
                val simpleNameA = a[0].javaClass.simpleName
                val simpleNameB = b[0].javaClass.simpleName
                Log.i(TAG, "getData: simpleNameA=${simpleNameA},simpleNameB=${simpleNameB}")
                var mapModel = TestMapModel(a, b)
                mapModel
            }.catch {
                Log.i(TestViewModel.TAG, "catch:error=" + it.message)
            }.onCompletion { cause ->
                Log.i(
                    TestViewModel.TAG,
                    "getReceivedEvent: onCompletion" + (cause?.message ?: "cause.is.null")
                )
            }.catch {
                Log.i(TestViewModel.TAG, "catch:2222222.error=" + it.message)
            }.collect {
                Log.i(TAG, "getData: success，userSize=${it.userList.size}, eventSize=${it.eventList.size}")
//                    eventUiModelLiveData.value = it
            }
        }
    }

    private fun testAA(){
    }
}