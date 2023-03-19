package me.aheadlcx.jetpack.flow

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import me.aheadlcx.github.R
import me.aheadlcx.github.databinding.ActivityFlowBinding
import me.aheadlcx.sp.SpAnrHelper
import me.aheadlcx.test.kotlin.SomeSingleton
import kotlin.system.measureTimeMillis


class FlowActivityUse : AppCompatActivity() {
    companion object {
        private const val TAG = "FlowActivityUse"
    }

    private val scope = MainScope()

    private lateinit var binding: ActivityFlowBinding

    private var manSharedFlow = MutableSharedFlow<String>(0, 10)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtClick.setOnClickListener {
//            clickFlow()
            showFlowOperator()
        }
        binding.txtClickAddData.setOnClickListener {
            addFlowData();
        }
        binding.txtClickAddCollect.setOnClickListener {
            addFlowCollect();
        }
        binding.txtSHowZip.setOnClickListener {
            showFlowZip()
        }
        binding.txtAddHotCollect.setOnClickListener {
            addHotCollect()
        }

        binding.txtLoadWebp.setOnClickListener {
            loadWebp()
        }

        binding.txtUnLoadWebp.setOnClickListener {
            unLoadWebp()
        }
        binding.txtAnr.setOnClickListener {
            applyInfo()
        }
        SpAnrHelper.tryHackActivityThreadH()
    }

    private fun unLoadWebp() {
        Glide.with(this).clear(binding.imgWebp);
    }

    private fun loadWebp() {
        Glide.with(this).load("https://isparta.github.io/compare-webp/image/gif_webp/webp/1.webp").into(binding.imgWebp)
    }

    private fun applyInfo() {
        val applySp: SharedPreferences =
            this.getSharedPreferences("apply", Context.MODE_PRIVATE)
        val applyEdit = applySp.edit()
        val content = "很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本" +
                ".很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本" +
                ".很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本" +
                ".很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本" +
                ".很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本.很长的文本"
        val newContent = this.resources.getString(R.string.longString)
        for (i in 1..1000) {
            val strKey = "str$i"
            applyEdit.putString(strKey, content + newContent)
            applyEdit.apply()
            Log.i(TAG, "applyInfo: i=$i")
        }
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }


    var countCollect: Int = 0;
    private fun addFlowCollect() {
        countCollect++;
        scope.launch {
            mutableSharedFlow.collect {
                Log.i("aheadlcx", "addFlowCollect." + it)
            }
        }
    }

    var count: Int = 0;
    private fun addFlowData() {
        count++;
        scope.launch {
            mutableSharedFlow.emit("addFlowData,count=$count")
        }
    }

    private fun clickFlow2() {
        scope.launch {
            val flowA = (1..5).asFlow()
            val flowB = flowOf("one", "two", "three", "four", "five")
            flowA.zip(flowB) { a, b ->
                a.toString() + b
            }.collect()
        }
    }

    private suspend fun testFlow() {
//        manSharedFlow.emit("aheadlcx")
        flow {
            for (i in 1..9) {
                emit(i)
            }
        }.collect(object : FlowCollector<Int> {
            override suspend fun emit(value: Int) {
                Log.i(TAG, "testFlow: $value")
            }

        })
//            .collect{
//            Log.i(TAG, "testFlow: $it")
//        }
    }

    //    val mutableSharedFlow = MutableSharedFlow<String>(0).shareIn(scope, SharingStarted.Eagerly, 1)
//    val mutableSharedFlow = MutableStateFlow<String>("origin")
    val mutableSharedFlow = MutableSharedFlow<String>()

    private fun addClickFlow() {
        scope.launch {
            mutableSharedFlow.collect {
                Log.i("aheadlcx", "addClickFlow." + it)
            }
        }
    }

    private fun showFlowOperator() {
        showFlowZip()
        showHotFlow()
    }

    private fun addHotCollect() {
        scope.launch {
            flowA.collect{
                Log.i(TAG, "addHotCollect: $it")
            }
        }
    }

    val flowA = flow {
        for (i in 1..5) {
            Log.i(TAG, "showHotFlow: $i")
            emit(i)
        }
    }.shareIn(scope, SharingStarted.Eagerly, 3)

    private fun showHotFlow() {
        Log.i(TAG, "showHotFlow: ------------")
        scope.launch {
            SomeSingleton.getInstance("ss")
        }
    }

    private fun showFlowMap() {
        scope.launch {
            flowA.flowWithLifecycle(this@FlowActivityUse.lifecycle, Lifecycle.State.RESUMED)
        }
    }

    private fun showFlowThread() {
        scope.launch {
            val flowA = flow {
                for (i in 1..5) {
                    if (i == 3) {
                        throw RuntimeException("3==i exception")
                    }
                    Log.i(TAG, "showFlowZip: $i")
                    emit(i)
                }
            }.flowOn(Dispatchers.Default)

        }

    }

    private fun showFlowZip() {
        Log.i(TAG, "showFlowZip: ------------")
        scope.launch {
            val flowA = flow {
                for (i in 1..5) {
                    if (i == 3) {
                        throw RuntimeException("3==i exception")
                    }
                    Log.i(TAG, "showFlowZip: $i")
                    emit(i)
                }
            }

            val flowB = flowOf("a", "b", "c", "d")
            flowA.zip(flowB) { a, b ->
                a.toString() + b
            }.onCompletion { cause ->
                Log.i(TAG, "showFlowZip: onCompletion=" + cause?.message)
            }.catch { cause ->
                Log.i(TAG, "showFlowZip: catch" + cause.message)
            }.map { value ->
                "value=" + value
            }
                .collect {
                    Log.i(TAG, "showFlowZip,collect: $it")
                }
        }
    }

    private fun clickFlow() {
        scope.launch {
            var time = measureTimeMillis {

                flow {
                    for (i in 1..5) {
                        Log.i(TAG, "clickFlow: emit.before.i= ${i}")
//                        delay(100)
                        if (i == 4) {
//                        throw Exception("自定义异常333")
                        }
                        emit(i)
                    }
                }.onCompletion { cause ->
                    Log.i(TAG, "clickFlow: onCompletion.cause.is.null=" + (cause == null))
                }.onStart {
                    Log.i(TAG, "clickFlow: onStart")
                }
                    .catch {
                        Log.i(TAG, "clickFlow: catch")
                    }.transform {
                        emit(it)
//                        emit(it * 3)
                    }
//                    .reduce{a, b ->
//                        a + b
//                    }
//                    .map {
//                        if (it == 3) {
////                    throw Exception("map异常")
//                        }
//                        return@map it.toString()
//                    }
                    .retry {
                        true
                    }
                    .catch {
//                Log.i(TAG, "clickFlow: map下面的异常")
                    }.buffer()
                    .collect {
//                        delay(100)
                        Log.i(TAG, "clickFlow.collect: ${it}")
//            }
//                    Log.i(TAG, "clickFlow: end")
                    }
            }
            Log.i(TAG, "clickFlow: time= ${time}")
        }

    }
}