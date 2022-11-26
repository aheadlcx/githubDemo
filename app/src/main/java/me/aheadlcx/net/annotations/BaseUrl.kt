package me.aheadlcx.net.annotations

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/26 5:38 下午
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class BaseUrl(val value: String)