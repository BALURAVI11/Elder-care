package com.explore.eldercare.ui.donation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.explore.eldercare.databinding.DonationListItemBinding
import com.explore.eldercare.databinding.HealthcareListItemBinding
import com.explore.eldercare.ui.healthcare.HealthcareData
import com.explore.eldercare.ui.notifications.data.Reminder

class DonationAdapter(private var list : List<DonationData>) : RecyclerView.Adapter<DonationAdapter.ViewHolder>(){
    inner class ViewHolder(val binding : DonationListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DonationListItemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DonationAdapter.ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.apply {
            tvDonationName.text = data.name
            tvDonationAddress.text = data.address
            tvDonationContact.text = data.contact
            tvDonationOccupants.text = "Occupants : ${data.occupants}"
            tvDonationDescription.text = data.description
            btnDonationDonate.setOnClickListener {
                overdueCallback(data.contact)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newData: List<DonationData>) {
        list = newData
        notifyDataSetChanged()
    }

    private lateinit var overdueCallback : (item: String) -> Unit

    fun overdueListener(callback: (item: String) -> Unit){
        overdueCallback = callback
    }
}