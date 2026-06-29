package com.explore.eldercare.ui.chatting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explore.eldercare.R
import com.explore.eldercare.ui.chatting.healthAndUser
import com.explore.eldercare.ui.models.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class messageAdapter (val context: Context, val list: List<MessageModel>) : RecyclerView.Adapter<messageAdapter.MessageViewHolder>(){

    val MSG_TYPE_RIGHT = 0
    val MSG_TYPE_LEFT = 1

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val text = itemView.findViewById<TextView>(R.id.messageText)
        val image = itemView.findViewById<ImageView>(R.id.senderImage)
    }

    override fun getItemViewType(position: Int): Int {
        return if(list[position].senderId == FirebaseAuth.getInstance().currentUser!!.uid){
            MSG_TYPE_RIGHT
        }else {
            MSG_TYPE_LEFT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if (viewType == MSG_TYPE_RIGHT){
            MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_reciever_message,parent,false))
        }else{
            MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_sender_message,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.text.text = list[position].message

        FirebaseDatabase.getInstance().getReference("healthAndUser")
            .child(list[position].senderId!!).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            val data =snapshot.getValue(healthAndUser::class.java)
                            try {
                                Glide.with(context).load(data!!.image).into(holder.image)
                            }catch (e: Exception){
                                Toast.makeText(context,"DAmn", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
                    }

                }
            )
    }
}