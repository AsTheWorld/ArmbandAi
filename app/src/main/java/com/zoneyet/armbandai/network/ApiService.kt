package com.zoneyet.armbandai.base

import android.webkit.WebSettings
import com.zoneyet.armbandai.MyApp
import com.zoneyet.armbandai.model.*
import io.reactivex.Observable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object ApiService {
    private const val BASE_URL = "http://172.17.24.99:8088/api/v1.0/armband_dev/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .addHeader("Authorization", MyApp.instance.getToken())
                .removeHeader("User-Agent")
                .addHeader("User-Agent", WebSettings.getDefaultUserAgent(MyApp.instance))
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "close")//解决 unexpected end of stream 问题
                .build()
            chain.proceed(newRequest)
        }
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

        @POST("patient/info")
        fun createPatient(
        @Body body: RequestBody
        ): Observable<BaseResponse<String>>

        @GET("hospital/default_hospital")
        fun getDefHospital(): Observable<BaseResponse<DefHospitalBean>>

        @GET("patient/info")
        fun getPatients(): Observable<BaseResponse<PatientListBean>>
    }



    fun login(username: String, password: String): Observable<LoginResponse> {
        return api.login(username, password)
    }
    fun getDefHospital(): Observable<BaseResponse<DefHospitalBean>> {
        return api.getDefHospital()
    }

    fun createPatient(
        id_card_number: String,
        disease_type: String,
        hospital_id: String,
        name: String,
        illness_desc: String,
        relation: Int,
    ): Observable<BaseResponse<String>> {
        val json = JSONObject()
        json.put("id_card_number", id_card_number)
        json.put("disease_type", disease_type)
        json.put("hospital_id", hospital_id)
        json.put("name", name)
        json.put("is_default", false)
        json.put("illness_desc", illness_desc)
        json.put("relation", relation)
        json.put("hospital_id", MyApp.instance.getSPString("def_hospital_id"))
        return api.createPatient(createRequestBody(json.toString()))
    }

    fun createRequestBody(json:String):RequestBody{
        return json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    fun create(): Api {
        return api
    }

    fun getPatients(): Observable<BaseResponse<PatientListBean>> {
        return api.getPatients()
    }
}

sealed class LoginResult {
    object Success : LoginResult()
    data class Failure(val error: String) : LoginResult()
}
