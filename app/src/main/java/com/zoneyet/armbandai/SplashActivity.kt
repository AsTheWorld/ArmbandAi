package com.zoneyet.armbandai

import android.content.Intent
import com.zoneyet.armbandai.base.BaseActivity
import com.zoneyet.armbandai.base.NoViewModel
import com.zoneyet.armbandai.databinding.ActivitySplashBinding
import com.zoneyet.armbandai.login.LoginActivity
import com.zoneyet.armbandai.main.MainActivity

class SplashActivity:BaseActivity<ActivitySplashBinding,NoViewModel>() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_splash
    }

    override fun initViewModel(): NoViewModel {
        return NoViewModel()
    }

    override fun initView() {
        val intent = if (MyApp.instance.getToken().isEmpty()) {
            Intent(this, LoginActivity::class.java)
        } else {
            Intent(this, MainActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

    override fun observeLiveData() {
    }
}