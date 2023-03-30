package com.zoneyet.armbandai.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zoneyet.armbandai.base.BaseViewModel

class MainViewModel  : BaseViewModel() {

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
