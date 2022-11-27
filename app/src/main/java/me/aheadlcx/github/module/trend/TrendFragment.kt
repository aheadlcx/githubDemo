package me.aheadlcx.github.module.trend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.aheadlcx.github.databinding.FragmentDynamicBinding
import me.aheadlcx.github.databinding.FragmentTrendBinding
import me.aheadlcx.github.module.base.BaseFragment

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 5:32 下午
 */
class TrendFragment:BaseFragment() {
    private lateinit var binding: FragmentTrendBinding
    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentTrendBinding.inflate(inflater)
        return binding.root
    }
}