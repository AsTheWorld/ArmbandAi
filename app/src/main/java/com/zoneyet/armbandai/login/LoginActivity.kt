package com.zoneyet.armbandai.login

import android.content.Intent
import androidx.lifecycle.Observer
import com.zoneyet.armbandai.R
import com.zoneyet.armbandai.base.BaseActivity
import com.zoneyet.armbandai.base.LoginResult
import com.zoneyet.armbandai.databinding.ActivityLoginBinding
import com.zoneyet.armbandai.main.MainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_login
    }

    override fun initViewModel(): LoginViewModel {
        return LoginViewModel()
    }

    override fun initView() {
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.loginResult.observe(this, Observer { result ->
            when (result) {
                is LoginResult.Success -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    // 处理登录失败
                }
            }
        })
    }
}
