package com.zoneyet.armbandai

import android.app.Application
import android.content.Context

class MyApp:Application() {
    // 单例
    companion object {
        lateinit var instance: MyApp
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val sharedPreferences by lazy {
        getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
    }

    fun getToken():String{
        return sharedPreferences.getString("token", "")!!
    }
    fun setToken(token:String){
        sharedPreferences.edit().putString("token", token).apply()
    }

    //生成设置SharedPreferences的方法
    fun setSPString(key:String,value:String){
        sharedPreferences.edit().putString(key, value).apply()
    }
    fun getSPString(key:String):String{
        return sharedPreferences.getString(key, "")!!
    }
}