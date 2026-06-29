package com.explore.eldercare.ui.contacts



import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.explore.eldercare.ui.contacts.retrofit.DoctorList
import com.explore.eldercare.ui.contacts.retrofit.RetrofitServiceInterface
import com.explore.eldercare.ui.contacts.retrofit.Retroinstance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.io.BufferedReader
import java.io.IOException


class ContactsViewModel : ViewModel() {
    private var doctors = MutableLiveData<List<DoctorList>>()

//    init {
//        loadDoctorsFromJson()
//    }
//
//    private fun loadDoctorsFromJson() {
//        try {
//            val inputStream: InputStream = (res.anim)
//            val jsonStr = inputStream.bufferedReader().use { it.readText() }
//            val gson = Gson()
//            val doctorList = gson.fromJson(jsonStr, Array<DoctorList>::class.java).toList()
//            doctors.value = doctorList
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//
//    fun getdata(): LiveData<List<DoctorList>> {
//        return doctors
//    }
}