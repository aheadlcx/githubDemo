package me.aheadlcx.github.test

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.aheadlcx.github.common.net.GsonUtils
import me.aheadlcx.github.databinding.FragmentDynamicBinding
import me.aheadlcx.github.databinding.FragmentTestBinding
import me.aheadlcx.github.module.base.BaseFragment
import me.aheadlcx.github.repository.UserRepository

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
            testCLick1()
        }
        binding.txtCLick2.setOnClickListener {
            testCLick2()
        }

        binding.txtCLick3.setOnClickListener {
            testClick3()
        }
    }

    private fun testClick3() {
        MainScope().launch {
            val replayCache = brandFlow.replayCache
            replayCache.forEach {
                Log.i(TAG, "testClick3: it=${it}")
            }
        }
    }

    fun testCLick1() {
        count++
        var content = "count=" + count
        Log.i(TAG, "testCLick1: ${content}")
        MainScope().launch {
            brandFlow_.emit(content)
        }
    }

    fun testCLick2() {
        Log.i(TAG, "testCLick2: ")
        MainScope().launch {
            brandFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect{

                }

            brandFlow.collect{
                Log.i(TAG, "testCLick第一个collect: it=${it}")
            }

            brandFlow.collect{
                Log.i(TAG, "testCLick第二个collect: it=${it}")
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