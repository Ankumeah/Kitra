package com.ankumeah.github.kitra.apiCalls

import com.ankumeah.github.kitra.classes.IsUserValidResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IsUserValidApi {
  @GET("/api/is_user_valid")
  suspend fun isUserValid(@Query("email") email: String): Response<IsUserValidResponse?>
}