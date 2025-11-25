package com.ankumeah.github.kitra.apiCalls

import com.ankumeah.github.kitra.classes.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
  @POST("/api/login")
  suspend fun sendLoginInfo(@Body body: Map<String, String>): Response<LoginResponse?>
}