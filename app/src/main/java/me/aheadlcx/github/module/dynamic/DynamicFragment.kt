package me.aheadlcx.github.module.dynamic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.loadState.trailing.TrailingLoadStateAdapter.OnTrailingListener
import me.aheadlcx.github.databinding.FragmentDynamicBinding
import me.aheadlcx.github.model.ui.EventUIModel
import me.aheadlcx.github.module.base.BaseFragment
import me.aheadlcx.github.ui.adapter.DynamicAdapter

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 5:31 下午
 */
class DynamicFragment : BaseFragment() {

    companion object {
        private const val TAG = "DynamicFragment"
    }

    private lateinit var binding: FragmentDynamicBinding
    private lateinit var dynamicViewModel: DynamicViewModel

    private val mAdapter: DynamicAdapter = DynamicAdapter()
    private lateinit var helper: QuickAdapterHelper

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentDynamicBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dynamicViewModel = ViewModelProviders.of(this).get(DynamicViewModel::class.java)

        binding.txtClick.setOnClickListener {
//            dynamicViewModel.getReceivedEvent(1)
            initData()
        }
        binding.txtClickUser.setOnClickListener {
            dynamicViewModel.getFollowers(1)
        }

        initRecycleView()
        addHeadView()
        initData()
        initLiveData()
    }

    private fun initLiveData() {
        dynamicViewModel.eventUiModelLiveData.observe(this.viewLifecycleOwner, object :
            Observer<List<EventUIModel>> {
            override fun onChanged(data: List<EventUIModel>?) {
                mAdapter.submitList(data)
                Log.i(TAG, "onChanged:data.size=${data?.size ?: -1}")
            }

        })
    }

    private fun initData() {
        request()
    }

    fun initRecycleView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        initAdapter()
    }

    private fun initAdapter() {
        // 使用默认的"加载更多"的样式
        val loadMoreListener: OnTrailingListener = object : OnTrailingListener {
            override fun onLoad() {
                request()
            }

            override fun onFailRetry() {
                request()
            }

            override fun isAllowLoading(): Boolean {
                return true
            }
        }
        helper = QuickAdapterHelper.Builder(mAdapter)
            .setTrailingLoadStateAdapter(loadMoreListener).build()

        // 设置预加载，请调用以下方法
        helper.trailingLoadStateAdapter?.preloadSize = 1
        binding.recyclerView.adapter = helper.adapter
    }

    private fun request() {
        Log.i(TAG, "request: ")
        dynamicViewModel.getReceivedEvent()
    }

    private fun addHeadView() {
//        val headerAdapter = HeaderAdapter()
//        headerAdapter.setOnItemClickListener { _, _, _ ->
//            addHeadView()
//        }
//        helper.addBeforeAdapter(0, headerAdapter)
    }


}