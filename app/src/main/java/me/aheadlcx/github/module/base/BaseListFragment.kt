package me.aheadlcx.github.module.base

import com.shuyu.commonrecycler.listener.OnItemClickListener
import com.shuyu.commonrecycler.listener.OnLoadingListener

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 8:07 下午
 */
abstract class BaseListFragment<R: BaseViewModel> :BaseFragment(), OnItemClickListener , OnLoadingListener {

    private lateinit var baseViewModel: R

}