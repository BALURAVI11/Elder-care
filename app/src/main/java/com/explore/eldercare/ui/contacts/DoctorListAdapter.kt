package com.explore.eldercare.ui.contacts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explore.eldercare.MainActivity
import com.explore.eldercare.R
import com.explore.eldercare.ui.contacts.retrofit.DoctorList


class DoctorListAdapter(private val dataList: ArrayList<DoctorList>): RecyclerView.Adapter<DoctorListAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(contactNo : String)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        return MyViewHolder(itemView)
    }

    private var doctorList: List<DoctorList>? = null

    fun setDoctorList(doctorList: List<DoctorList>?){
        this.doctorList = doctorList
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]

        holder.username.text = data.username
        holder.email.text = data.email
        holder.experience.text = data.experience
        holder.specialization.text = data.specialization
        holder.description.text = data.description
        holder.phone.text= data.phone
        if(data.image!=null) {
            Glide.with(holder.itemView.context).load(data.image).into(holder.pfp)
            //Picasso.get().load(data.image).into(holder.pfp)
        }

        holder.button.setOnClickListener {
//            val validNumber = Regex("^[+]?[0-9]{10}$")
//            val validNumber2 = Regex("^[+]"+"91"+"[+]?[0-9]{10}$")

//            if (data.contactNo.matches(validNumber
//                ) or data.contactNo.matches(validNumber2)) {

          //      val call: Uri = Uri.parse("tel:${data.phone}")
  //              val intent = Intent(Intent.ACTION_DIAL, call)
 //               startActivity(intent)

            mListener.onItemClick(data.phone!!)

            }
        }

    override fun getItemCount(): Int {
        return dataList.size
    }





    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var pfp : ImageView = itemView.findViewById(R.id.pfp)
        var username : TextView= itemView.findViewById(R.id.username)
        var email : TextView= itemView.findViewById(R.id.email)
        var experience: TextView = itemView.findViewById(R.id.experience)
        var specialization :TextView= itemView.findViewById(R.id.specialization)
        var description:TextView = itemView.findViewById(R.id.description)
        var phone:TextView = itemView.findViewById(R.id.phonenumber)
        var button:Button =itemView.findViewById(R.id.button)

    }



}