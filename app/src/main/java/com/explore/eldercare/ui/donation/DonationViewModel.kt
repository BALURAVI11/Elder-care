package com.explore.eldercare.ui.donation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.explore.eldercare.ui.healthcare.HealthcareData
import com.explore.eldercare.ui.models.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class DonationViewModel : ViewModel() {

    private val _list = MutableLiveData<List<DonationData>>()
    val list: LiveData<List<DonationData>> get() = _list
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    private lateinit var database: DatabaseReference

    fun getList() {
        viewModelScope.launch {
            _loading.value = true
            val newList = mutableListOf<DonationData>()
            database = Firebase.database.reference
            database.child("oldagehome").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (item in snapshot.children) {
                            val newData = item.getValue(DonationData::class.java)
                            if (newData != null) {
                                newList.add(newData)
                            }
                            _list.value = newList

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