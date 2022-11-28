package me.aheadlcx.github.test

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
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
    
    private lateinit var binding: FragmentTestBinding
    private val userRepo: UserRepository = UserRepository()
    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentTestBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreateView(mainView: View?) {
        super.onCreateView(mainView)
        binding.txtClick.setOnClickListener {
            onClickUser()
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