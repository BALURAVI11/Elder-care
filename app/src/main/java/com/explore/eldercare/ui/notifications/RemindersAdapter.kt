package com.explore.eldercare.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.explore.eldercare.databinding.ReminderListItemBinding
import com.explore.eldercare.ui.notifications.data.Reminder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class RemindersAdapter : RecyclerView.Adapter<RemindersAdapter.ViewHolder>() {

    private var listOf = listOf<Reminder>()

    fun setReminders(list: Flow<List<Reminder>>){
        GlobalScope.launch {
            list.collect(FlowCollector {
                listOf = it
            })
            notifyDataSetChanged()
        }

    }

    private lateinit var overdueCallback : (item: Reminder) -> Unit

    fun overdueListener(callback: (item: Reminder) -> Unit){
        overdueCallback = callback
    }
    class ViewHolder(val binding : ReminderListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ReminderListItemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOf.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.rvReminderListItemMessage.text = listOf[position].message
        holder.binding.rvReminderListItemFrequency.text = listOf[position].frequency
        holder.binding.btnDelete.setOnClickListener {
            overdueCallback(listOf[position])
        }
    }
}