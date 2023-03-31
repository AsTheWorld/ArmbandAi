package com.zoneyet.armbandai.login

import androidx.lifecycle.MutableLiveData
import com.zoneyet.armbandai.MyApp
import com.zoneyet.armbandai.base.ApiService
import com.zoneyet.armbandai.base.BaseViewModel
import com.zoneyet.armbandai.base.LoginResult
import com.zoneyet.armbandai.model.LoginResponse
import com.zoneyet.armbandai.utils.MD5Util
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel : BaseViewModel() {
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginResult = MutableLiveData<LoginResult>()

    fun login() {
        val apiService = ApiService.create()
        apiService.login(username.value!!, MD5Util.encrypt(password.value!!))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<LoginResponse> {
                override fun onSubscribe(d: Disposable) {
                    // do nothing
                }

                override fun onNext(loginResponse: LoginResponse) {
                    if (loginResponse.code == 200 && loginResponse.status == 0) {
                        loginResponse.data.token?.let { token ->
                            MyApp.instance.setToken(token)
                        }
                        loginResult.value = LoginResult.Success
                    } else {
                        loginResult.value = LoginResult.Failure("登录失败")
                    }
                }

                override fun onError(e: Throwable) {
                    loginResult.value = LoginResult.Failure("登录失败")
                }

                override fun onComplete() {
                    // do nothing
                }
            })
    }

    override fun onStart() {
    }

    override fun onStop() {
    }
}
