package com.zoneyet.armbandai.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoneyet.armbandai.R
import com.zoneyet.armbandai.base.BaseActivity
import com.zoneyet.armbandai.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private var currentFragment: Fragment? = null


    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): MainViewModel {
        return MainViewModel()
    }

    override fun initView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        navController = Navigation.findNavController(this, R.id.fragment_container)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        // 监听底部导航栏的选中事件
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> navController.navigate(R.id.homeFragment)
                R.id.navigation_dashboard -> navController.navigate(R.id.dashboardFragment)
                R.id.navigation_notifications -> navController.navigate(R.id.notificationsFragment)
                R.id.navigation_profile -> navController.navigate(R.id.profileFragment)
            }
            true
        }

        // 监听导航栏中的fragment切换事件
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val fragment = supportFragmentManager.findFragmentById(destination.id)
            if (fragment != null && fragment != currentFragment) {
                if (currentFragment != null) {
                    supportFragmentManager.beginTransaction().hide(currentFragment!!).commit()
                }
                supportFragmentManager.beginTransaction().show(fragment).commit()
                currentFragment = fragment
            }
        }
        viewModel.getDefHospitalId()

    }

    override fun observeLiveData() {
    }
}
