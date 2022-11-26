package me.aheadlcx.jetpack.coroutine

import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import me.aheadlcx.github.databinding.ActivityCoroutineBinding
import me.aheadlcx.github.databinding.ActivityLiferecycleBinding

class CoroutineActivity : AppCompatActivity(), CoroutineScope by MainScope(){


    private val scope = MainScope()

    companion object {
        private const val TAG = "CoroutineActivity"
    }

    private lateinit var binding: ActivityCoroutineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textClick.setOnClickListener{
            clickCoroutine()
        }
    }

    private fun clickCoroutine() {
        Log.i(TAG, "clickCoroutine: ")
        lifecycleScope.launchWhenResumed {

        }
        launch {
            CoroutineShowCase().methodOne()

        }
        scope.launch {
            coroutineScope{
                val async = async {
                    delay(2000)
                    100
                }
            }
        }


        val job = scope.launch {
            Log.i(TAG, "clickCoroutine: launch isMainThread=${isMainThread()}")
        }

        val async = scope.async {
            Log.i(TAG, "clickCoroutine: async isMainThread=${isMainThread()}")
            "aa"
        }
        scope.launch {
            val await = async.await()
            Log.i(TAG, "clickCoroutine: " )
//            Log.i(TAG, "clickCoroutine: " + await)
        }
        val main = Dispatchers.Main
    }

    private fun isMainThread():Boolean{
        return Looper.getMainLooper() == Looper.myLooper()
    }

    private fun testCoroutine(){
        val job = CoroutineScope(Dispatchers.Main).launch {

            async(Dispatchers.IO) {
                YYLogUtils.w("切换到一个协程1")
                delay(5000)
                YYLogUtils.w("协程1执行完毕")
            }

            launch {
                YYLogUtils.w("切换到一个协程2")
                delay(2000)
                YYLogUtils.w("协程2执行完毕")
            }

            GlobalScope.launch {
                YYLogUtils.w("切换到一个协程3")
                delay(3000)
                YYLogUtils.w("协程3执行完毕")
            }



        }

        MainScope().launch(job!!) {
//            MainScope().launch() {
            YYLogUtils.w("切换到一个协程4")
            delay(4000)
            YYLogUtils.w("协程4执行完毕")
        }



    }

    override fun onDestroy() {
        super.onDestroy()
//        job.cancel()
    }
}