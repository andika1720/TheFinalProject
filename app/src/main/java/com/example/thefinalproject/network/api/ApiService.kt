package com.example.thefinalproject.network.api


import com.example.thefinalproject.network.model.CategoryResponse
import com.example.thefinalproject.network.model.DataCategory
import com.example.thefinalproject.network.model.ListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("course")
    suspend fun getlist():ListResponse

    @GET("course")
    suspend fun getCategory():CategoryResponse

    @GET("course/{id}")
    suspend fun getDataById(@Query("id") id: String): DataCategory
}