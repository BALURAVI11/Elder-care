package com.explore.eldercare.ui.healthcare

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.explore.eldercare.ui.chatting.ChatActivity
import com.explore.eldercare.ui.models.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class HealthCareViewModel : ViewModel() {

    private val _list = MutableLiveData<List<HealthcareData>>()
    val list: LiveData<List<HealthcareData>> get() = _list
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    private lateinit var database: DatabaseReference

    fun getList() {
        viewModelScope.launch {
            _loading.value = true
            val newList = mutableListOf<HealthcareData>()
            database = Firebase.database.reference
            database.child("healthcare").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (item in snapshot.children) {
                            database.child("users").child(item.key!!).addValueEventListener(object : ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val uid:String= item.key!!
                                    if (snapshot.exists()) {
                                        val newData = snapshot.getValue(Users::class.java)
                                        val newInnerData = HealthcareData(
                                            uid = uid,
                                            name = newData?.name!!,
                                            age = newData.age!!,
                                            experience = newData.experience!!,
                                            address = newData.address!!,
                                            image = newData.image!!,
                                            email = newData.email!!
                                        )
                                        newList.add(newInnerData)
                                        _list.value = newList
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {

                                }
                            })

                        }
                        _loading.value = false
                    } else {
                        _loading.value = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i("error", error.message)
                    _loading.value = false
                }
            })
        }
    }




}