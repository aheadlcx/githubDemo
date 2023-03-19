package me.aheadlcx.github.test

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import me.aheadlcx.github.common.net.GsonUtils
import me.aheadlcx.github.databinding.FragmentTestBinding
import me.aheadlcx.github.module.base.BaseFragment
import me.aheadlcx.github.repository.UserRepository
import me.aheadlcx.jetpack.livedata.LiveDataBus
import java.util.*
import kotlin.collections.HashMap

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/28 7:17 下午
 */
class TestFragment : BaseFragment() {
    companion object {
        private const val TAG = "TestFragment"
    }

//    var brandFlow_ = MutableSharedFlow<String>(10)
//    var brandFlow = brandFlow_.asSharedFlow()

    var brandFlow_ = MutableStateFlow<String>("origin")
    var brandFlow = brandFlow_.asStateFlow()

    var count = 1
    val viewModel by viewModel { TestViewModel(1) }

    private lateinit var binding: FragmentTestBinding
    private val userRepo: UserRepository = UserRepository()
    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentTestBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        binding.txtClick.setOnClickListener {
//            onClickUser()
//            testCLick1()
            testException()
        }
        binding.txtCLick2.setOnClickListener {
            testCLick2()
        }

        binding.txtCLick3.setOnClickListener {
            testClick3()
        }
    }

    fun test2023(){
        val provider = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return super.create(modelClass)
            }
        })
        provider.get(TestViewModel::class.java)
    }

    private fun testException(){
//        viewModel.testZip()
        viewModel.getData()
    }

    private fun test(){

//        observer.javaClass.isAssignableFrom(LiveDataBus.ObserverWrapper::class.java)
//        Objects.hash()
//        Objects.equals()
        viewModel.eventUiModelLiveData.observe(this){

        }

        binding.txtCLick4.text = "aheadlcx"
    }

    private fun testClick3() {
        MainScope().launch(Dispatchers.IO) {
            val replayCache = brandFlow.replayCache
            replayCache.forEach {
                Log.i(TAG, "testClick3: it=${it}")
            }
        }
    }

    suspend fun testCLick1() {
        Glide.with(this).load("").skipMemoryCache(true)
        var mutex = Mutex()
        mutex.lock()

        mutex.unlock()
        mutex.withLock {

        }

        count++
        var content = "count=" + count
        Log.i(TAG, "testCLick1: ${content}")
        MainScope().launch {
            brandFlow_.emit(content)
        }
    }

    fun testCLick2() {
        Log.i(TAG, "testCLick2: ")
        brandFlow_.launchAndCollectIn {
            Log.i(TAG, "launchAndCollectIn: ${it}")
        }
        brandFlow_.launchAndCollectIn2(this) {
            Log.i(TAG, "launchAndCollectIn2: ${it}")
        }
//        MainScope().launch {
//            brandFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
//                .collect{
//
//                }
//
//            brandFlow.collect{
//                Log.i(TAG, "testCLick第一个collect: it=${it}")
//            }
//
//            brandFlow.collect{
//                Log.i(TAG, "testCLick第二个collect: it=${it}")
//            }
//        }
    }

    // I do not known why this turn red tips
    inline fun <T> Flow<T>.launchAndCollectIn2(
        owner: LifecycleOwner,
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        crossinline action: suspend CoroutineScope.(T) -> Unit
    ) = owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(minActiveState) {
            collect {
                action(it)
            }
        }
    }


    private fun onClickUser() {
        Log.i(TAG, "onClickUser: begin")
        MainScope().launch {
            userRepo.getPersonUserInfo()
                .collect {
                    var userJson = GsonUtils.toJsonString(it)
                    Log.i(TAG, "onClickUser: userJson=${userJson}")

                }
        }
    }


}