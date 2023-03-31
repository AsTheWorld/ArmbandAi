package com.zoneyet.armbandai.model

data class BaseResponse<T:Any> (
    val status: Int,
    val msg: String,
    val data: T,
    val code: Int
)