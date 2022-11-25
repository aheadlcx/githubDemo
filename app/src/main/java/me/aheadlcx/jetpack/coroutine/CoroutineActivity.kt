package me.aheadlcx.jetpack.coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import me.aheadlcx.github.databinding.ActivityCoroutineBinding
import me.aheadlcx.github.databinding.ActivityLiferecycleBinding

class CoroutineActivity : AppCompatActivity() {


    private val scope = MainScope()

    companion object {
        private const val TAG = "CoroutineActivity"
    }

    private lateinit var binding: ActivityCoroutineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textClick.setOnClickListener{
            clickCoroutine()
        }

    }

    private fun clickCoroutine() {
        val main = Dispatchers.Main
    }
}