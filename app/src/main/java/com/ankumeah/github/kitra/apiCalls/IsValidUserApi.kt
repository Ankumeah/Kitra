package com.ankumeah.github.kitra.apiCalls

import com.ankumeah.github.kitra.classes.IsValidUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IsValidUserApi {
  @POST("/api/is_valid_user")
  suspend fun isValidUser(@Body body: Map<String, String>): Response<IsValidUserResponse?>
}