package com.zoneyet.armbandai.fragment.patient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.zoneyet.armbandai.databinding.ItemPatientBinding
import com.zoneyet.armbandai.model.PatientModel

class PatientListAdapter(private val glide: RequestManager) :
    ListAdapter<PatientModel, PatientListAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPatientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, glide)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val patient = getItem(position)
        holder.bind(patient)
    }

    class ViewHolder(private val binding: ItemPatientBinding, private val glide: RequestManager) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(patient: PatientModel) {
            binding.patientName.text = patient.name
            binding.patientDescription.text = patient.illness_desc
            glide.load(patient.avatarUrl).into(binding.patientAvatar)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PatientModel>() {
            override fun areItemsTheSame(oldItem: PatientModel, newItem: PatientModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PatientModel, newItem: PatientModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
