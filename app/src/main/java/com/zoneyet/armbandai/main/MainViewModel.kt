package com.zoneyet.armbandai.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zoneyet.armbandai.MyApp
import com.zoneyet.armbandai.base.ApiService
import com.zoneyet.armbandai.base.BaseViewModel
import com.zoneyet.armbandai.base.LoginResult
import com.zoneyet.armbandai.model.BaseResponse
import com.zoneyet.armbandai.model.DefHospitalBean
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel  : BaseViewModel() {

    private val mText = MutableLiveData<String>()

    init {
        mText.value = "This is home fragment"
    }

    fun getText(): LiveData<String> {
        return mText
    }

    fun getDefHospitalId() {
        ApiService.getDefHospital()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<DefHospitalBean>> {
                override fun onSubscribe(d: Disposable) {
                    // do nothing
                }

                override fun onNext(response: BaseResponse<DefHospitalBean>) {
                    if (response.code == 200 && response.status == 0) {
                        MyApp.instance.setSPString("def_hospital_id", response.data.id)
                    } else {
                    }
                }

                override fun onError(e: Throwable) {
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
