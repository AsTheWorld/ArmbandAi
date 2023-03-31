package com.zoneyet.armbandai.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected lateinit var viewModel: VM
    protected lateinit var binding: VB

    /**
     * 子类需实现该方法，返回ViewModel的class
     */
    protected abstract fun getViewModelClass(): Class<VM>

    /**
     * 子类需实现该方法，返回布局的layout id
     */
    protected abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this).get(getViewModelClass())
        initView()
        initData()
        return binding.root
    }

    /**
     * 初始化View
     */
    open fun initView() {}

    /**
     * 初始化数据
     */
    open fun initData() {}
}
