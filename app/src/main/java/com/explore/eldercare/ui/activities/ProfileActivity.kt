package com.explore.eldercare.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.explore.eldercare.R
import com.explore.eldercare.databinding.ActivityProfileBinding
import com.explore.eldercare.ui.models.Users
import com.explore.eldercare.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Config.showDialog(this)

        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).get()
            .addOnSuccessListener {
                if(it.exists()){
                    val data= it.getValue(Users::class.java)

                    binding.name.setText(data!!.name.toString())
                    binding.age.setText(data!!.age.toString())
                    binding.gender.setText(data!!.gender.toString())
                    binding.height.setText(data!!.height.toString())
                    binding.weight.setText(data!!.weight.toString())
                    binding.experience.setText(data!!.experience.toString())
                    binding.prev.setText(data!!.ailments.toString())
                    binding.Bloodgroup.setText(data!!.bloodgroup.toString())



                    Glide.with(this).load(data.image).placeholder(R.drawable.images)
                        .into(binding.pfp)

                    Config.hideDialog()
                }

                binding.logout.setOnClickListener{
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }

                binding.edit.setOnClickListener{
                    startActivity(Intent(this, EditProfileActivity::class.java))
                }
            }.addOnFailureListener{
                Config.hideDialog()
            }
    }
}