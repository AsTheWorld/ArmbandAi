package com.zoneyet.armbandai.fragment.patient

import androidx.lifecycle.MutableLiveData
import com.zoneyet.armbandai.base.ApiService
import com.zoneyet.armbandai.base.BaseViewModel
import com.zoneyet.armbandai.model.BaseResponse
import com.zoneyet.armbandai.model.PatientListBean
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PatientListViewModel:BaseViewModel() {

    val patientList = MutableLiveData<PatientListBean>()

    fun refresh() {
        ApiService.getPatients()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<PatientListBean>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<PatientListBean>) {
                    if (t.code == 200 && t.status == 0) {
                        patientList.value = t.data
                    }
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }



    override fun onStart() {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }
}
