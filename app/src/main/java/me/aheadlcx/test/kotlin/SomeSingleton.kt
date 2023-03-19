package me.aheadlcx.test.kotlin

/**
 * Description:
 * author: aheadlcx
 * Date:2023/2/24 11:53 上午
 */
class SomeSingleton(name: String) {
    companion object{
        private var name = ""
        private val instance : SomeSingleton by lazy ( mode = LazyThreadSafetyMode.SYNCHRONIZED){
            SomeSingleton(name)
        }

        fun getInstance(s: String): SomeSingleton{
            this.name = s;
            return instance
        }
    }

    fun  printS(){

    }
}