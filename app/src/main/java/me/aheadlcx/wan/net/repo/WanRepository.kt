package me.aheadlcx.wan.net.repo

import me.aheadlcx.wan.net.bean.WanListBean

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/20 8:10 下午
 */
class WanRepository {
    fun getWanList():List<WanListBean>{
        var list =  ArrayList<WanListBean>()
        list.add(WanListBean("aheadlcx"))
        list.add(WanListBean("lopend"))
        list.add(WanListBean("macbookpro"))
        return list
    }
}