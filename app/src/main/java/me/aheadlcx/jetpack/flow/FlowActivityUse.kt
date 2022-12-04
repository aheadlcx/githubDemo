package me.aheadlcx.jetpack.flow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.aheadlcx.github.databinding.ActivityFlowBinding
import me.aheadlcx.github.databinding.ActivityLiferecycleBinding
import kotlin.system.measureTimeMillis

class FlowActivityUse : AppCompatActivity() {
    companion object {
        private const val TAG = "FlowActivityUse"
    }

    private val scope = MainScope()

    private lateinit var binding: ActivityFlowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtClick.setOnClickListener {
            clickFlow()
        }
    }

    private fun clickFlow2() {
        scope.launch {
            val flowA = (1..5).asFlow()
            val flowB = flowOf("one", "two", "three", "four", "five")
            flowA.zip(flowB) {
                a, b->
                a.toString() + b
            }.collect()
        }
    }

    private fun clickFlow() {
        scope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

            }
            val mutableSharedFlow = MutableSharedFlow<String>(10)
            mutableSharedFlow.flowWithLifecycle(this@FlowActivityUse.lifecycle, Lifecycle.State.STARTED)
        }


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