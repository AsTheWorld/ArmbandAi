package com.zoneyet.armbandai.fragment.home

import androidx.lifecycle.MutableLiveData
import com.zoneyet.armbandai.base.ApiService
import com.zoneyet.armbandai.base.BaseViewModel
import com.zoneyet.armbandai.model.BaseResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel : BaseViewModel() {
    val createPatientLiveData = MutableLiveData<Boolean>()

    fun addPatient(
        name: String,
        idCard: String,
        relationship: Int,
        disease: String,
        description: String
    ) {
        ApiService.createPatient(
            id_card_number = idCard,
            name = name,
            relation = relationship,
            disease_type=disease,
            illness_desc = description,
            hospital_id = ""
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<String>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<String>) {
                    createPatientLiveData.value = t.code == 200 && t.status == 0
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }


            })
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

}
