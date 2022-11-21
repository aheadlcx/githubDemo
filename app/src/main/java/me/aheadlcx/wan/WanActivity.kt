package me.aheadlcx.wan

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.aheadlcx.github.api.adapter.ApiResult
import me.aheadlcx.github.databinding.ActivityMainBinding
import me.aheadlcx.github.databinding.ActivityWanBinding
import me.aheadlcx.wan.net.WanRetrofitUtil

import me.aheadlcx.util.rxLaunch
import me.aheadlcx.wan.net.bean.Banner2

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/20 7:01 下午
 */
class WanActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "WanActivity"
    }

    var scope = MainScope()

    private lateinit var binding: ActivityWanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtGet.setOnClickListener {
            getWanDataRx()
        }
        binding.txtTest.setOnClickListener {
            testException()
        }
    }

    private fun testException() {
        scope.launch {
            flow<String> {
                val haha = "retrofit"
                if (haha.contains("retrofit")) {
                    throw Exception("exception.retrofit")
                } else {
                    emit(haha)
                }
            }.flowOn(Dispatchers.IO)
                .onCompletion { cause ->
//                    run {
                        if (cause != null) {
                            Log.i(TAG, "testException.onCompletion.error: " + cause.message)
                        } else {
                            Log.i(TAG, "testException.onCompletion.success: ")
                        }

//                    }
                }.catch { cause ->
                    Log.i(TAG, "testException.catch: ${cause.message}")
                }
                .collect {
                    Log.i(TAG, "testException.collect.data:${it}")

                }
        }
    }


    private fun getWanDataRx() {
        scope.rxLaunch<Banner2> {
            onRequest = {
                WanRetrofitUtil.getWanService().banner2()
            }
            onSuccess = {
                Log.i(TAG, "getWanData.success")
                for (bannerItem in it.data) {
                    if (!TextUtils.isEmpty(bannerItem.desc)) {
                        binding.txtShow.text = bannerItem.desc
                        break
                    }
                }
            }
            onError = {
                Log.i(TAG, "getWanData.error")

            }

        }
    }


    private fun getWanData() {
        scope.launch {
            val banner = WanRetrofitUtil.getWanService().banner()
            if (banner is ApiResult.BizSuccess) {
                Log.i(TAG, "getWanData.success")
                for (bannerItem in banner.data) {
                    if (!TextUtils.isEmpty(bannerItem.desc)) {
                        binding.txtShow.text = bannerItem.desc
                        break
                    }
                }
            } else {
                Log.i(TAG, "getWanData.error")
            }
        }
    }
}