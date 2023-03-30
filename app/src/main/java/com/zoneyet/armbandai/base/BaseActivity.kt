package com.zoneyet.armbandai.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var binding: VDB
    protected lateinit var viewModel: VM

    abstract fun getLayoutResId(): Int
    abstract fun initViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        viewModel = initViewModel()
        binding.lifecycleOwner = this
        initView()
        observeLiveData()
    }

    abstract fun initView()
    abstract fun observeLiveData()
}
