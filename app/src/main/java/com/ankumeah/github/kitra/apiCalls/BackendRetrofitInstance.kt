package com.ankumeah.github.kitra.apiCalls

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object BackendRetrofitInstance {
  private val retrofit by lazy {
    Retrofit.Builder().baseUrl("https://192.168.43.115:5000/")
      .client(unsafeOkHttpClient()) //TODO(): Remove in Prod
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  val loginApi: LoginApi by lazy { retrofit.create(LoginApi::class.java) }
  val logoutApi: LogoutApi by lazy { retrofit.create(LogoutApi::class.java) }
  val renewSessionTokenApi: RenewSessionTokenApi by lazy { retrofit.create(RenewSessionTokenApi::class.java) }
  val isValidUserApi: IsValidUserApi by lazy { retrofit.create(IsValidUserApi::class.java) }
}

fun unsafeOkHttpClient(): OkHttpClient {
  val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
  })

  val sslContext = SSLContext.getInstance("SSL")
  sslContext.init(null, trustAllCerts, SecureRandom())

  val sslSocketFactory = sslContext.socketFactory

  return OkHttpClient.Builder()
    .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
    .hostnameVerifier { _, _ -> true }
    .build()
}