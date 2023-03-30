package com.zoneyet.armbandai.model

data class Data(
    val token: String,
    val id: String,
    val type: Int,
    val mobile: String,
    val userId: String,
    val nickname: String,
    val phone: String,
    val avatar_url: String
)

data class LoginResponse(
    val status: Int,
    val msg: String,
    val data: Data,
    val code: Int
)
