package me.aheadlcx.jetpack.net.service.bean

class BaseBizResponse<T>(var errorCode: Int, var errorMsg: String, var data: T) {
}