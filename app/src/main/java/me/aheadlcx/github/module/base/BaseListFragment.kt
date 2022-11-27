package me.aheadlcx.github.module.base

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 8:07 下午
 */
abstract class BaseListFragment<R: BaseListViewModel> :BaseFragment(),
    AdapterView.OnItemClickListener {
//    protected var normalAdapterManager by autoCleared<BindingDataRecyclerManager>()
    private lateinit var baseViewModel: R

//    var adapter by autoCleared<RecyclerView.Adapter>()
    lateinit var adapter :RecyclerView.Adapter<RecyclerView.ViewHolder>

    abstract protected fun getViewModelProvider(): ViewModelProvider.Factory

    override fun onCreateView(mainView: View?) {
//        normalAdapterManager = BindingDataRecyclerManager()
        baseViewModel = ViewModelProviders.of(this, getViewModelProvider())
            .get(getViewModelClass())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()

        getViewModel().loading.observe(viewLifecycleOwner, Observer {
            when (it) {
                LoadState.RefreshDone -> {
                    refreshComplete()
                }
                LoadState.LoadMoreDone -> {
                    loadMoreComplete()
                }
                LoadState.Refresh -> {
                    ///刷新时清空旧数据
                }
            }
        })

        getViewModel().dataList.observe(viewLifecycleOwner, Observer { items ->
//            items?.apply {
//                if (items.size > 0) {
//                    if (getViewModel().isFirstData()) {
//                        adapter?.dataList?.clear()
//                    }
//                    val currentSize: Int = adapter?.dataList?.size ?: 0
//                    adapter?.dataList?.addAll(items)
//                    if (currentSize == 0) {
//                        notifyChanged()
//                    } else {
//                        notifyInsert(currentSize, items.size)
//                    }
//                } else {
//                    if (getViewModel().isFirstData()) {
//                        adapter?.dataList?.clear()
//                        notifyChanged()
//                    }
//                }
//            }
        })

        getViewModel().needMore.observe(this.viewLifecycleOwner, Observer { it ->
//            it?.apply {
//                normalAdapterManager?.setNoMore(!it)
//            }
        })

        showRefresh()
    }


//    /**
//     * item点击
//     */
//    override fun onItemClick(context: Context, position: Int) {
//
//    }
//
//    /**
//     * 刷新
//     */
//    override fun onRefresh() {
//        getViewModel().refresh()
//    }
//
//    /**
//     * 加载更多
//     */
//    override fun onLoadMore() {
//        getViewModel().loadMore()
//    }

    /**
     * 当前 recyclerView，为空即不走 @link[initList] 的初始化
     */
    abstract fun getRecyclerView(): RecyclerView?

//    /**
//     * 绑定Item
//     */
//    abstract fun bindHolder(manager: BindSuperAdapterManager)

    /**
     * ViewModel Class
     */
    abstract fun getViewModelClass(): Class<R>

    /**
     * ViewModel
     */
    open fun getViewModel(): R = baseViewModel

    /**
     * 是否需要下拉刷新
     */
    open fun enableRefresh(): Boolean = false

    /**
     * 是否需要下拉刷新
     */
    open fun enableLoadMore(): Boolean = false

    open fun refreshComplete() {
//        normalAdapterManager?.refreshComplete()
    }

    open fun loadMoreComplete() {
//        normalAdapterManager?.loadMoreComplete()
    }

    open fun showRefresh() {
//        normalAdapterManager?.setRefreshing(true)
    }

    open fun isLoading(): Boolean = getViewModel().isLoading()

    open fun notifyInsert(position: Int, count: Int) {
//        adapter?.notifyItemRangeInserted(position + adapter!!.absFirstPosition(), count)
    }

    open fun notifyDelete(position: Int, count: Int) {
//        adapter?.dataList?.removeAt(position)
//        adapter?.notifyItemRangeRemoved(position + adapter!!.absFirstPosition(), count)
    }

    open fun notifyChanged() {
        adapter?.notifyDataSetChanged()
    }

    fun initList() {
        if (activity != null && getRecyclerView() != null) {
            val recyclerView = getRecyclerView()
//            adapter = ..
            recyclerView?.layoutManager = LinearLayoutManager(requireActivity())
            recyclerView?.adapter = adapter
        }
    }


}