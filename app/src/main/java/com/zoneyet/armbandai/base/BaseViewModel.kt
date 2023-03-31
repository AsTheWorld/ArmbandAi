package com.zoneyet.armbandai.base
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    abstract fun onStart()
    abstract fun onStop()
}