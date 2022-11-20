package me.aheadlcx.wan

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.MainScope
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