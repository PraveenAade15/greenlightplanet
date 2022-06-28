package com.example.greenlight.api

import com.example.greenlight.models.AreaResponse
import com.example.greenlight.models.ResponseData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v3/286f38b4-c6c1-4348-aabc-6d396dcbc4de")
    suspend fun getArea():Response<AreaResponse>
}