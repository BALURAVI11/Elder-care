package com.explore.eldercare.ui.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.explore.eldercare.MainActivity
import com.explore.eldercare.R
import com.explore.eldercare.databinding.ActivityChatBinding
import com.explore.eldercare.ui.chatting.adapter.messageAdapter
import com.explore.eldercare.ui.models.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        verifyChatId()

        binding.imageView8.setOnClickListener{
            if(binding.message.text!!.isEmpty()){
                Toast.makeText(this,"Please enter your text", Toast.LENGTH_SHORT).show()

            }else{
                val rr=FirebaseDatabase.getInstance().getReference("healthAndUser")
                    .child(intent.getStringExtra("uid")!!)

                val uid = intent.getStringExtra("uid")

                val namee=intent.getStringExtra("name")
                val emaill=intent.getStringExtra("email")
                val image=intent.getStringExtra("image")

                val data=healthAndUser(
                    image=image,
                    name=namee,
                    email=emaill,
                    uid=uid
                )
                rr.setValue(data).addOnCompleteListener{
                    if(it.isSuccessful){

                        Toast.makeText(this,"message sent successfully", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
                storeData(binding.message.text.toString())
            }
        }

    }

    private var senderId: String? = null
    private var chatId: String? = null
    private var recieverId: String? = null

    private fun verifyChatId() {

        senderId= FirebaseAuth.getInstance().currentUser!!.uid
        recieverId = intent.getStringExtra("uid")

        chatId=senderId+recieverId
        val reverseChatId = recieverId+senderId

        val reference = FirebaseDatabase.getInstance().getReference("chats")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(chatId!!)){
                    getData(chatId)
                }else if(snapshot.hasChild(reverseChatId)){
                    chatId=reverseChatId
                    getData(chatId)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChatActivity,"Something went wrong",Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun getData(chatId: String?){
        FirebaseDatabase.getInstance().getReference("chats").child(chatId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = arrayListOf<MessageModel>()

                    for (show in snapshot.children){
                        list.add(show.getValue(MessageModel::class.java)!!)
                    }

                    binding.recyclerView2.adapter = messageAdapter(this@ChatActivity,list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatActivity,"jjj",Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun storeData( msg: String) {

        val currentDate:String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentTime:String = SimpleDateFormat("HH:mm a", Locale.getDefault()).format(Date())

        val map= hashMapOf<String,String>()
        map["message"]=msg
        map["senderId"]=senderId!!
        map["currentTime"]=currentTime
        map["currentDate"]=currentDate

        val reference = FirebaseDatabase.getInstance().getReference("chats").child(chatId!!)

        reference.child(reference.push().key!!)
            .setValue(map).addOnCompleteListener{
                if(it.isSuccessful){
                    binding.message.text= null

                    Toast.makeText(this,"Message Sent",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }

    }
}