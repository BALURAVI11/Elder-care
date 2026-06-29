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
import com.explore.eldercare.ui.models.Users
import com.explore.eldercare.utils.Config
import com.explore.eldercare.utils.Config.hideDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class MakeProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMakeProfileBinding
    private var imageUri : Uri? = null

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUri = it
        binding.image.setImageURI(imageUri)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.image.setOnClickListener{
            selectImage.launch("image/*")
        }

        binding.upload.setOnClickListener{
            validateData()
        }
    }

    private fun validateData(){
        if(binding.name.text.toString().isEmpty()||binding.age.text.toString().isEmpty()||binding.adress.text.toString().isEmpty()||
            binding.Bloodgroup.text.toString().isEmpty()||binding.experience.text.toString().isEmpty()||binding.gender.text.toString().isEmpty()
            ||binding.heightcms.text.toString().isEmpty()||binding.weightkgs.text.toString().isEmpty()||binding.pastailments.text.toString().isEmpty()
            ||imageUri==null){
            Toast.makeText(this,"Please complete your information",Toast.LENGTH_SHORT).show()
        }else{
            UploadImage()
        }
    }

    private fun UploadImage(){
        Config.showDialog(this)

        val storageRef = FirebaseStorage.getInstance().getReference("profile")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("profile.jpg")

        storageRef.putFile(imageUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    storeData(it)
                }.addOnFailureListener{
                    hideDialog()
                    Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun storeData(imageUrl: Uri?){
        val data = Users(
            name = binding.name.text.toString(),
            email =FirebaseAuth.getInstance().currentUser!!.email,
            age =binding.age.text.toString(),
            gender =binding.gender.text.toString(),
            height =binding.heightcms.text.toString(),
            weight =binding.weightkgs.text.toString(),
            ailments =binding.pastailments.text.toString(),
            experience =binding.experience.text.toString(),
            bloodgroup =binding.Bloodgroup.text.toString(),
            address =binding.adress.text.toString(),
            image =imageUrl.toString(),
        )

        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(data).addOnCompleteListener{
                if(it.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    Toast.makeText(this,"User register sucessful", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}