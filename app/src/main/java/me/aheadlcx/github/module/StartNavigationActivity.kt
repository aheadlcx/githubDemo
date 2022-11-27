package me.aheadlcx.github.module

import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import me.aheadlcx.github.R
import me.aheadlcx.github.module.base.BaseActivity

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 3:03 下午
 */
class StartNavigationActivity: BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startnavigation)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.primaryNavigationFragment
        if (fragment is NavHostFragment) {
//            if (fragment.navController.currentDestination?.id == R.id.loginFragment) {
//                super.onBackPressed()
//            } else if (fragment.navController.currentDestination?.id == R.id.loginOAuthFragment) {
//                fragment.navController.navigate(R.id.loginFragment,
//                    null, NavOptions.Builder().setPopUpTo(fragment.navController.graph.id,
//                        true).build())
//            }
        }
    }

}