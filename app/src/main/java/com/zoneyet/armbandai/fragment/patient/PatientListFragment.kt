package com.zoneyet.armbandai.fragment.patient

import com.bumptech.glide.Glide
import com.zoneyet.armbandai.R
import com.zoneyet.armbandai.base.BaseFragment
import com.zoneyet.armbandai.databinding.FragmentPatientListBinding

class PatientListFragment : BaseFragment<FragmentPatientListBinding, PatientListViewModel>() {

    private lateinit var patientListAdapter: PatientListAdapter

    override fun getViewModelClass(): Class<PatientListViewModel> {
        return PatientListViewModel::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_patient_list
    }

    override fun initView() {
        super.initView()
        // 初始化RecyclerView
        patientListAdapter = PatientListAdapter(Glide.with(this))
        binding.recyclerView.adapter = patientListAdapter

        // 初始化下拉刷新
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    override fun initData() {
        super.initData()
        // 监听患者列表数据的变化
        viewModel.patientList.observe(viewLifecycleOwner) {
            patientListAdapter.submitList(it.results)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        // 初始化数据
        viewModel.refresh()
    }
}
