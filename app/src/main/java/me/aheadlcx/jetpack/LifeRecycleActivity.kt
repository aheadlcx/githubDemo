package me.aheadlcx.jetpack

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.aheadlcx.github.databinding.ActivityLiferecycleBinding
import me.aheadlcx.github.databinding.ActivityWanBinding
import me.aheadlcx.jetpack.net.FlowRetrofitUtil
import me.aheadlcx.jetpack.net.service.WanAndroidService
import me.aheadlcx.jetpack.net.service.weather.ApiServiceManager
import me.aheadlcx.net.People
import me.aheadlcx.wan.WanActivity

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/21 9:01 下午
 */
class LifeRecycleActivity : AppCompatActivity() {

    private val scope = MainScope()

    companion object {
        private const val TAG = "LifeRecycleActivity"
    }

    private lateinit var binding: ActivityLiferecycleBinding
    private var peopleLiveData: MutableLiveData<People> = MutableLiveData<People>()
    private var count = 1

    private val myViewModel by lazy {
        ViewModelProvider(this).get("12", MyViewModel::class.java).apply {
            nameLiveData.observe(this@LifeRecycleActivity, {
                Log.i(TAG, "从ViewModel收到的数据: ${it}")
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiferecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        peopleLiveData.observe(this, Observer { people ->
            Log.i(TAG, "onCreate:收到新的人，名字是=${people.name}")
            if (people == null) {

            }
        })

        binding.txtClick.setOnClickListener {
//            clickItem()
//            addLogLifecycle()
//            testFlow()
            testWanFlowService()
//            getWeatherNetData()
        }
    }

    private fun testWanFlowService() {
        Log.i(TAG, "testWanFlowService: ")
        scope.launch(Dispatchers.IO) {
            val service = FlowRetrofitUtil.getRetrofit().create(WanAndroidService::class.java)
            service.getBanner()
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.i(TAG, "testWanFlowService: 异常")
                }.collect {
                    Log.i(TAG, "testWanFlowService: 请求数据成功")
                    for (item in it.data) {
                        Log.i(TAG, "testWanFlowService: 描述是" + item.desc)
                    }
                }
        }
    }

    private fun getWeatherNetData() {
        Log.i(TAG, "getWeatherNetData:beigin")
        MainScope().launch {
            ApiServiceManager.weatherApiService.getWeatherInfoNow(location = "北京")
                //transform data,if you want to
                .map {
                    Log.i(TAG, "getWeatherNetData: map ${it}")
                    return@map it
                    // ...
                }
                //subscribe on io dispatcher
                .flowOn(Dispatchers.IO)
                //catch errors
                .catch { ex ->
                    println("error occurs:$ex")
                    Log.i(TAG, "getWeatherNetData: " + "error occurs:${ex}")
                }
                //subscribe data
                .collect {
                    println("weather info:$it")
                    Log.i(TAG, "getWeatherNetData: " + "weather info:$it")
                }
        }
    }

    private fun testFlow() {
        scope.launch {

            flow {
                for (i in 1..5) {
                    delay(100)
                    Log.i(LifeRecycleActivity.TAG, "testFlow: 准备emit")
                    emit(i)
                }
            }.collect {
                Log.i(LifeRecycleActivity.TAG, "testFlow: ${it}")
            }
        }
    }

    fun addLogLifecycle() {
        lifecycle.addObserver(object : LifecycleObserver {

        })
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                Log.i(TAG, "onCreate: ")
            }

            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                Log.i(TAG, "onStart: ")
            }

            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                Log.i(TAG, "onResume: ")
            }
        })
    }

    fun clickItem() {
        count++
        Log.i(TAG, "clickItem: ")
        var newName = "周杰伦" + count
        var people = People(newName)
        peopleLiveData.value = people

        binding.txtClickCount.text = newName
    }
}