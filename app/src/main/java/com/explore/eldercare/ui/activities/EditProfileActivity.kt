package com.explore.eldercare.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.explore.eldercare.MainActivity
import com.explore.eldercare.R
import com.explore.eldercare.databinding.ActivityEditProfileBinding
import com.explore.eldercare.ui.models.Users
import com.explore.eldercare.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private var uri : Uri? = null
    private var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = Users()


        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    data = it.getValue(Users::class.java) ?: Users()

                    binding.etEditName.setText(data.name.toString())
                    binding.etEditAge.setText(data.age.toString())
                    binding.etEditGender.setText(data.gender.toString())
                    binding.etEditHeight.setText(data.height.toString())
                    binding.etEditWeight.setText(data.weight.toString())
                    binding.etEditExperience.setText(data.experience.toString())
                    binding.etEditPrev.setText(data.ailments.toString())
                    binding.etEditBloodgroup.setText(data.bloodgroup.toString())
                    binding.etEditEmail.setText(data.email.toString())
                    binding.etEditAddress.setText(data.address.toString())


                    Glide.with(this).load(data.image).placeholder(R.drawable.images)
                        .into(binding.etEditPfp)

                    Config.hideDialog()
                }

                binding.ivEdit.setOnClickListener {
                    showAvatarSelectorDialog(data)
                }

                binding.etEditPfp.setOnClickListener {
                    showAvatarSelectorDialog(data)
                }

                binding.btnEditUpdate.setOnClickListener {
                    var newInfo = Users(
                        name = binding.etEditName.text.toString(),
                        age = binding.etEditAge.text.toString(),
                        gender = binding.etEditGender.text.toString(),
                        height = binding.etEditHeight.text.toString(),
                        weight = binding.etEditWeight.text.toString(),
                        experience = binding.etEditExperience.text.toString(),
                        ailments = binding.etEditPrev.text.toString(),
                        bloodgroup = binding.etEditBloodgroup.text.toString(),
                        email = binding.etEditEmail.text.toString(),
                        address = binding.etEditAddress.text.toString(),
                        image = data.image
                    )
                    if(flag && uri != null){
                        val storageRef = FirebaseStorage.getInstance().getReference("profile")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("profile.jpg")

                        storageRef.putFile(uri!!)
                            .addOnSuccessListener {
                                storageRef.downloadUrl.addOnSuccessListener {link->
                                    newInfo = newInfo.copy(image = link.toString())
                                    FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(newInfo).addOnCompleteListener{
                                            if(it.isSuccessful){
                                                startActivity(Intent(this, MainActivity::class.java))
                                                finish()
                                                Toast.makeText(this,"User Profile Update Successful", Toast.LENGTH_SHORT).show()
                                            }else{
                                                Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                }.addOnFailureListener{
                                    Config.hideDialog()
                                    Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                                }
                            }

                    }else{
                        FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(newInfo).addOnCompleteListener{
                                if(it.isSuccessful){
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                    Toast.makeText(this,"User Profile Update Successful", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                    }

                }
            }

    }

    private fun showAvatarSelectorDialog(data: Users) {
        val dialog = android.app.Dialog(this)
        dialog.setContentView(R.layout.dialog_avatar_selector)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val avatarBlue = dialog.findViewById<com.google.android.material.imageview.ShapeableImageView>(R.id.avatar_blue_btn)
        val avatarOrange = dialog.findViewById<com.google.android.material.imageview.ShapeableImageView>(R.id.avatar_orange_btn)
        val avatarTeal = dialog.findViewById<com.google.android.material.imageview.ShapeableImageView>(R.id.avatar_teal_btn)
        val avatarPurple = dialog.findViewById<com.google.android.material.imageview.ShapeableImageView>(R.id.avatar_purple_btn)
        val btnGallery = dialog.findViewById<com.google.android.material.button.MaterialButton>(R.id.btn_select_gallery)

        val onAvatarSelected = { resId: Int ->
            uri = Uri.parse("android.resource://${packageName}/$resId")
            flag = true
            Glide.with(this).load(uri).into(binding.etEditPfp)
            dialog.dismiss()
        }

        avatarBlue.setOnClickListener { onAvatarSelected(R.drawable.avatar_blue) }
        avatarOrange.setOnClickListener { onAvatarSelected(R.drawable.avatar_orange) }
        avatarTeal.setOnClickListener { onAvatarSelected(R.drawable.avatar_teal) }
        avatarPurple.setOnClickListener { onAvatarSelected(R.drawable.avatar_purple) }

        btnGallery.setOnClickListener {
            dialog.dismiss()
            Intent(Intent.ACTION_GET_CONTENT).also { intent->
                intent.type = "image/*"
                launcher.launch(intent)
            }
        }

        dialog.show()
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            (
                    if (result.resultCode == RESULT_OK) {
                        result?.data?.data?.let {
                            uri = it
                            flag = true
                            applicationContext?.let { it1 -> Glide.with(it1).load(it).into(binding.etEditPfp) }
                        }
                    }
                    )

        }
}