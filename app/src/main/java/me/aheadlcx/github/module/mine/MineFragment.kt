package me.aheadlcx.github.module.mine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.aheadlcx.github.databinding.FragmentDynamicBinding
import me.aheadlcx.github.databinding.FragmentMineBinding
import me.aheadlcx.github.module.base.BaseFragment

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 5:35 下午
 */
class MineFragment:BaseFragment() {
    private lateinit var binding: FragmentMineBinding
    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMineBinding.inflate(inflater)
        return binding.root
    }
}