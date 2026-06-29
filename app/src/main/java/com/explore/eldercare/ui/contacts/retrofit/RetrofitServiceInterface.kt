package com.explore.eldercare.ui.contacts.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServiceInterface {
    @GET("/users/")
    fun getDoctorList(): Call<List<DoctorList>>
}