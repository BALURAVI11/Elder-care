package com.explore.eldercare.ui.chatting.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explore.eldercare.databinding.UserInteractedLayoutBinding
import com.explore.eldercare.ui.chatting.ChatActivity
import com.explore.eldercare.ui.chatting.healthAndUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class chatAdapter (val context: Context, val list: ArrayList<String>, val chatKey: List<String>) : RecyclerView.Adapter<chatAdapter.ChatViewHolder>(){

    inner class ChatViewHolder(val binding : UserInteractedLayoutBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(UserInteractedLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        Log.d("ppp","hhh")

        FirebaseDatabase.getInstance().getReference().child("healthAndUser")
            .child(list[position]).addListenerForSingleValueEvent(

                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.d("kyu","list[position].toString()")
                        if (snapshot.exists()){
                            val data =snapshot.getValue(healthAndUser::class.java)
                            Log.d("kk",data!!.image!!)
                            Log.d("ll",data.name!!)
                            Glide.with(context).load(data!!.image).into(holder.binding.userImage)

                            holder.binding.userName.text = data.name
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
                    }

                }
            )

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("uid",list[position])
            context.startActivity(intent)
        }
    }
}