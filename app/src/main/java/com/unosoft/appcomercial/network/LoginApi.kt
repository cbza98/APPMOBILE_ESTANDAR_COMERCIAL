package com.unosoft.appcomercial.network

import com.unosoft.appcomercial.models.login.Login
import com.unosoft.appcomercial.models.login.LoginResponse
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers

interface LoginApi {
    @POST("api/Users/Login")
    @Headers("Content-Type:application/json")
    fun login(@Body data: Login?): Call<LoginResponse?>?
}