package me.aheadlcx.github.module.dynamic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import me.aheadlcx.github.databinding.FragmentDynamicBinding
import me.aheadlcx.github.databinding.FragmentLoginOauthBinding
import me.aheadlcx.github.module.base.BaseFragment
import me.aheadlcx.github.module.base.autoCleared
import me.aheadlcx.github.module.login.LoginViewModel

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 5:31 下午
 */
class DynamicFragment : BaseFragment() {
    private lateinit var binding: FragmentDynamicBinding
    private lateinit var loginViewModel: DynamicViewModel


    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentDynamicBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProviders.of(this).get(DynamicViewModel::class.java)

        binding.txtClick.setOnClickListener {
            loginViewModel.getReceivedEvent(1)
        }
        binding.txtClickUser.setOnClickListener {
            loginViewModel.getFollowers(1)
        }
    }
}