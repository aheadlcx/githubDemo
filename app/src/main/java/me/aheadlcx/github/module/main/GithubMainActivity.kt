package me.aheadlcx.github.module.main

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import me.aheadlcx.github.R
import me.aheadlcx.github.databinding.ActivityGithubmainBinding
import me.aheadlcx.github.databinding.ActivityMainBinding
import me.aheadlcx.github.module.base.BaseActivity
import me.aheadlcx.github.module.dynamic.DynamicFragment
import me.aheadlcx.github.module.mine.MineFragment
import me.aheadlcx.github.module.trend.TrendFragment
import me.aheadlcx.github.test.TestFragment
import me.aheadlcx.github.ui.adapter.FragmentPagerViewAdapter

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 5:11 下午
 */
class GithubMainActivity : BaseActivity() {

    companion object {
        private const val TAG = "GithubMainActivity"
    }

    private lateinit var binding: ActivityGithubmainBinding
    lateinit var mainFragmentList: List<Fragment>

    var tabs = ArrayList<String>();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubmainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainFragmentList = listOf(
            TestFragment(), DynamicFragment(), TrendFragment(),
            MineFragment()
        )
        initViewPager()
    }

    private fun initViewPager() {
        tabs.add("测试")
        tabs.add("动态")
        tabs.add("流行")
        tabs.add("我的")

        var viewPager = binding.homeViewPager
        var tabLayout = binding.homeTabBar
        tabs.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }

        viewPager.adapter = FragmentPagerViewAdapter(tabs, mainFragmentList, supportFragmentManager)
        viewPager.offscreenPageLimit = mainFragmentList.size

        tabLayout.setupWithViewPager(viewPager, false)

    }
}