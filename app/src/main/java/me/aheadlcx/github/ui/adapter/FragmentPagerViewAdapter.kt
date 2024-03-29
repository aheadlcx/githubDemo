package me.aheadlcx.github.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * Fragment的ViewPager适配器
 * Created by guoshuyu
 * Date: 2018-09-28
 */

class FragmentPagerViewAdapter(private var titleS:List<String>, private val fragmentList:
List<Fragment>,
                               supportFragmentManager:
FragmentManager) : FragmentPagerAdapter(supportFragmentManager) {

    override fun getCount(): Int {
        return fragmentList.size
    }


    override fun getItem(position: Int): Fragment {
       return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleS[position]
    }
}