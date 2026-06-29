package com.explore.eldercare.ui.contacts.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retroinstance {
    companion object{
        val BASE_URL = "http://localhost:8000/users/"

        fun getRetroInstance(): Retrofit{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}