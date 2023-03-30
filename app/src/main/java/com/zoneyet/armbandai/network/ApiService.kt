package com.zoneyet.armbandai.base

import com.zoneyet.armbandai.model.LoginResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

object ApiService {
    private const val BASE_URL = "http://172.17.24.99:8088/api/v1.0/armband_dev/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val api = retrofit.create(Api::class.java)

    interface Api {
        @FormUrlEncoded
        @POST("armband/user/login/password")
        fun login(
            @Field("phone") username: String,
            @Field("password") password: String
        ): Observable<LoginResponse>
    }



    fun login(username: String, password: String): Observable<LoginResponse> {
        return api.login(username, password)
    }

    fun create(): Api {
        return api
    }
}

sealed class LoginResult {
    object Success : LoginResult()
    data class Failure(val error: String) : LoginResult()
}
