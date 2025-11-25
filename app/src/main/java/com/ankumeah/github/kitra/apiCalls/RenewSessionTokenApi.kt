package com.ankumeah.github.kitra.apiCalls

import com.ankumeah.github.kitra.classes.RenewSessionTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RenewSessionTokenApi {
  @POST("/api/renew_session_token")
  suspend fun renewSessionToken(@Body body: Map<String, String>): Response<RenewSessionTokenResponse?>
}