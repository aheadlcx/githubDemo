package me.aheadlcx.github.module.dynamic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.aheadlcx.github.databinding.FragmentDynamicBinding
import me.aheadlcx.github.databinding.FragmentLoginOauthBinding
import me.aheadlcx.github.module.base.BaseFragment

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 5:31 下午
 */
class DynamicFragment: BaseFragment() {
    private lateinit var binding: FragmentDynamicBinding
    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentDynamicBinding.inflate(inflater)
        return binding.root
    }
}