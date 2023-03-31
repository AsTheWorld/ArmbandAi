package com.zoneyet.armbandai.fragment.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.*
import androidx.core.content.ContextCompat
import com.zoneyet.armbandai.R
import com.zoneyet.armbandai.base.BaseFragment
import com.zoneyet.armbandai.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {


    // 在顶部声明身份证号码输入框

    private val REQUEST_PERMISSION_CODE = 101
    private val REQUEST_IMAGE_CODE = 102

    // 头像 Uri
    private var avatarUri: Uri? = null

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        super.initView()
        binding.confirmButton.setOnClickListener { confirmAddPatient() }

        setupRelationshipSpinner()
        setupDiseaseSpinner()


        // 选择头像按钮点击事件处理
        binding.avatarImageView.setOnClickListener {
            // 检查是否已经获得存储权限
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    requireContext(),
                    "请先允许访问存储权限",
                    Toast.LENGTH_SHORT
                ).show()
                // 申请存储权限
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION_CODE
                )

                return@setOnClickListener
            }

            // 打开图片选择器
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_IMAGE_CODE)
        }
    }


    
    private fun setupRelationshipSpinner() {
        val relationshipList =
            listOf("爸爸", "妈妈", "爷爷", "奶奶", "外婆", "外公", "哥哥", "姐姐", "叔叔", "阿姨", "伯父", "伯母")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, relationshipList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.relationshipSpinner.adapter = adapter
    }

    private fun setupDiseaseSpinner() {
        val diseaseList = listOf(
            "普通癫痫患者",
            "婴儿痉挛患者",
            "Dravet综合征患者",
            "失神癫痫患者",
            "结节性硬化患者",
            "癫痫病灶切除术后患者",
            "迷走神经刺激术后患者"
        )
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, diseaseList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.diseaseSpinner.adapter = adapter
    }

    // 判断身份证号码是否有效
    private fun isValidIdCard(idCard: String): Boolean {
        if (TextUtils.isEmpty(idCard)) {
            return false
        }

        if (idCard.length != 18) {
            return false
        }

        val charArray = idCard.toCharArray()
        for (i in charArray.indices) {
            if (i == 17 && charArray[i] == 'x') {
                continue
            }
            if (charArray[i] < '0' || charArray[i] > '9') {
                return false
            }
        }

        return true
    }

    private fun confirmAddPatient() {
        val name = binding.nameEditText.text.toString()
        val relationship = binding.relationshipSpinner.selectedItemPosition
        val disease = binding.diseaseSpinner.selectedItem.toString()
        val description = binding.descriptionEditText.text.toString()
        val idCard = binding.idCardEditText.text.toString().trim()
        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(requireContext(), "姓名和病例描述不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        if (!isValidIdCard(idCard)) {
            Toast.makeText(requireContext(), "身份证号输入不正确", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.addPatient(name,idCard, relationship, disease, description)

        Toast.makeText(requireContext(), "添加成功", Toast.LENGTH_SHORT).show()
        clearInputFields()
    }

    private fun clearInputFields() {
        binding.nameEditText.setText("")
        binding. relationshipSpinner.setSelection(0)
        binding.diseaseSpinner.setSelection(0)
        binding.descriptionEditText.setText("")
        binding.idCardEditText.setText("")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 选择头像按钮点击事件处理
                binding.avatarImageView.performClick()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CODE && resultCode == Activity.RESULT_OK && data != null) {
            // 获取选中图片的 Uri
            avatarUri = data.data
            // 显示选中的图片
            binding.avatarImageView.setImageURI(avatarUri)
        }
    }


}
