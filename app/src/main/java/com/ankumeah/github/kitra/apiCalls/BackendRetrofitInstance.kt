package com.ankumeah.github.kitra.apiCalls

import com.ankumeah.github.kitra.objects.OkHttpClients
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendRetrofitInstance {
  private const val BASE_URL: String = "https://192.168.43.116/"
  private val retrofit by lazy {
    Retrofit.Builder().baseUrl(BASE_URL)
      .client(OkHttpClients.SharedOkHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  val loginApi: LoginApi by lazy { retrofit.create(LoginApi::class.java) }
  val logoutApi: LogoutApi by lazy { retrofit.create(LogoutApi::class.java) }
  val renewSessionTokenApi: RenewSessionTokenApi by lazy { retrofit.create(RenewSessionTokenApi::class.java) }
  val isUserValidApi: IsUserValidApi by lazy { retrofit.create(IsUserValidApi::class.java) }
}

