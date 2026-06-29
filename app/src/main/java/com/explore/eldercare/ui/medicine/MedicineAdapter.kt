package com.explore.eldercare.ui.medicine

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explore.eldercare.R
import com.explore.eldercare.databinding.HealthcareListItemBinding
import com.explore.eldercare.databinding.MedicineListItemBinding
import com.explore.eldercare.ui.healthcare.HealthcareData

class MedicineAdapter(private var list:List<MedicineData>) : RecyclerView.Adapter<MedicineAdapter.ViewHolder>() {
    class ViewHolder(val binding : MedicineListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MedicineListItemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.apply {
            tvMedicineName.text = data.name
            tvMedicineTreatment.text = data.treatment
            Glide.with(holder.itemView.context).load(data.image).placeholder(R.drawable.images)
                .into(ivMedicine)
            btnBuy.text = "Buy-${data.price}"
            btnBuy.setOnClickListener {
                overdueCallback(data)
            }
        }
    }

    private lateinit var overdueCallback : (item: MedicineData) -> Unit

    fun overdueListener(callback: (item: MedicineData) -> Unit){
        overdueCallback = callback
    }

    fun setData(newData: List<MedicineData>) {
        list = newData
        notifyDataSetChanged()
    }
}