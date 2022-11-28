package me.aheadlcx.github.ui.adapter

import android.content.Context
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.shuyu.github.kotlin.model.bean.Event
import me.aheadlcx.github.databinding.LayoutEventItemBinding
import me.aheadlcx.github.model.ui.EventUIModel

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/28 10:01 上午
 */
class DynamicAdapter : BaseQuickAdapter<EventUIModel?, DynamicAdapter.VH>() {
    companion object{
        private const val TAG = "DynamicAdapter"
    }

    class VH(
        parent: ViewGroup,
        val viewBinding: LayoutEventItemBinding = LayoutEventItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(viewBinding.root)



    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    protected override fun onBindViewHolder(holder: VH, position: Int, item: EventUIModel?) {
        holder.viewBinding.eventUIModel = item
        Log.i(TAG, "onBindViewHolder:position=${position}")
    }

}