package com.zoneyet.armbandai.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zoneyet.armbandai.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    private val mText = MutableLiveData<String>()

    init {
        mText.value = "This is home fragment"
    }

    fun getText(): LiveData<String> {
        return mText
    }

    override fun onStart() {
    }

    override fun onStop() {
    }
}
