package com.explore.eldercare.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.explore.eldercare.MainActivity
import com.explore.eldercare.R
import com.explore.eldercare.databinding.ActivityMakeProfileBinding
import com.explore.eldercare.databinding.ActivityRegisterHealthcareBinding
import com.explore.eldercare.ui.dashboard.DashboardFragment
import com.explore.eldercare.ui.models.HealthCare
import com.explore.eldercare.ui.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterHealthcare : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterHealthcareBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterHealthcareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.register.setOnClickListener{
            updatedata()
        }
    }

    private fun updatedata(){
        if(binding.education.text.toString().isEmpty()||binding.experience.text.toString().isEmpty()||binding.adress.text.toString().isEmpty()||
            binding.degree.text.toString().isEmpty()||binding.gender.text.toString().isEmpty()
            ||binding.moreaboutyou.text.toString().isEmpty()||binding.feepay.text.toString().isEmpty()){
            Toast.makeText(this,"Please complete your information", Toast.LENGTH_SHORT).show()
        }else{
            storeData()
        }
    }

    private fun storeData(){
        val data = HealthCare(
            education = binding.education.text.toString(),
            degree = binding.degree.text.toString(),
            moreaboutyou =binding.moreaboutyou.text.toString(),
            gender =binding.gender.text.toString(),
            feepay =binding.feepay.text.toString(),
            experience =binding.experience.text.toString(),
            adress =binding.adress.text.toString(),
        )

        FirebaseDatabase.getInstance().getReference("healthcare")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(data).addOnCompleteListener{
                if(it.isSuccessful){
                    startActivity(Intent(this, DashboardFragment::class.java))
                    finish()
                    Toast.makeText(this,"Healthcare register sucessful", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}