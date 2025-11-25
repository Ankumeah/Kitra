package com.ankumeah.github.kitra.apiCalls

import com.ankumeah.github.kitra.classes.LogoutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LogoutApi {
  @POST("api/logout")
  suspend fun logout(@Body body: Map<String, String>): Response<LogoutResponse?>
}