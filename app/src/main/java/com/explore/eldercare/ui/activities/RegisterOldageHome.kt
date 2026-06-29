package com.explore.eldercare.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.explore.eldercare.MainActivity
import com.explore.eldercare.R
import com.explore.eldercare.databinding.ActivityMakeProfileBinding
import com.explore.eldercare.databinding.ActivityRegisterOldageHomeBinding
import com.explore.eldercare.ui.dashboard.DashboardFragment
import com.explore.eldercare.ui.models.OldageHome
import com.explore.eldercare.ui.models.Users
import com.explore.eldercare.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class RegisterOldageHome : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterOldageHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterOldageHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.upload.setOnClickListener{
            validateData()
        }
    }

    private fun validateData(){
        if(binding.name.text.toString().isEmpty()||binding.description.text.toString().isEmpty()||binding.adress.text.toString().isEmpty()||
            binding.noOccupants.text.toString().isEmpty()||binding.noOccupants.text.toString().isEmpty()){
            Toast.makeText(this,"Please complete your information", Toast.LENGTH_SHORT).show()
        }else{
            storeData()
        }
    }


    private fun storeData(){
        val data = OldageHome(
            name = binding.name.text.toString(),
            description =binding.description.text.toString(),
            occupants =binding.noOccupants.text.toString(),
            contact =binding.contact.text.toString(),
            address =binding.adress.text.toString(),
        )

        FirebaseDatabase.getInstance().getReference("oldagehome")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(data).addOnCompleteListener{
                if(it.isSuccessful){
                    startActivity(Intent(this, DashboardFragment::class.java))
                    finish()
                    Toast.makeText(this,"Registered sucessfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}