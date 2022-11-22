package me.aheadlcx.jetpack

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import me.aheadlcx.github.databinding.ActivityLiferecycleBinding
import me.aheadlcx.github.databinding.ActivityWanBinding
import me.aheadlcx.net.People

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/21 9:01 下午
 */
class LifeRecycleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LifeRecycleActivity"
    }

    private lateinit var binding: ActivityLiferecycleBinding
    private var peopleLiveData: MutableLiveData<People> = MutableLiveData<People>()
    private var count = 1


    private val myViewModel by lazy {
        ViewModelProvider(this).get("12", MyViewModel::class.java).apply {
            nameLiveData.observe(this@LifeRecycleActivity, {
                Log.i(TAG, "从ViewModel收到的数据: ${it}")
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiferecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        peopleLiveData.observe(this, Observer { people ->
            Log.i(TAG, "onCreate:收到新的人，名字是=${people.name}")
            if (people == null) {

            }
        })

        binding.txtClick.setOnClickListener {
//            clickItem()
            addLogLifecycle()
        }
    }

    fun addLogLifecycle() {
        lifecycle.addObserver(object : LifecycleObserver {

        })
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                Log.i(TAG, "onCreate: ")
            }

            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                Log.i(TAG, "onStart: ")
            }

            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                Log.i(TAG, "onResume: ")
            }
        })
    }

    fun clickItem() {
        count++
        Log.i(TAG, "clickItem: ")
        var newName = "周杰伦" + count
        var people = People(newName)
        peopleLiveData.value = people

        binding.txtClickCount.text = newName
    }
}