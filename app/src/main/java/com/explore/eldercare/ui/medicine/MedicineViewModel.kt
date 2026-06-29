package com.explore.eldercare.ui.medicine

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONArray

class MedicineViewModel(application: Application) : AndroidViewModel(application) {

    private val _list = MutableLiveData<List<MedicineData>>()
    val list: LiveData<List<MedicineData>> get() = _list
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getList(){
        viewModelScope.launch {
            _loading.value = true
            val newList = mutableListOf<MedicineData>()
            val jsonStr = getApplication<Application>().applicationContext
                .assets.open("medicine.json").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonStr)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val medicineData = MedicineData(
                    name = jsonObject.getString("name"),
                    price = jsonObject.getString("price"),
                    image = jsonObject.getString("image"),
                    treatment = jsonObject.getString("treatment")
                )
                newList.add(medicineData)
            }
            _list.value = newList
            _loading.value = false
        }
    }
}