package com.zoneyet.armbandai.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PatientModel (
    var id: String = "",
    var is_default: Boolean? = false,//是否默认患者
    var name: String? = null,//患者姓名
    var disease_type: String? = null,//患病类型
    var age: Int = 0,// 年龄
    var gender: Int = 0,// 性别
    var address: String? = null,//住址
    @SerializedName("avatar_url")
    var avatarUrl: String? = null,//患者头像
    var id_card_number: String? = null,//身份证号码
    var account: String? = null,//Account
    var doctor_id: String? = null,//Doctor id
    var hospital: String? = null,//
    var hospital_name: String? = null,//
    var hospital_lng: String? = null,//
    var weight: String? = null,//体重/kg
    var illness_status: Int = 0,// 病情状态((1, '良好'), (2, '适中'), (3, '严重'))
    var mobile: String? = null,//Mobile
    var hospital_lat: String? = null,//Hospital lat
    var group: String? = null,//Group
    var group_admin_id: String? = null,//Group
    var isSelect: Boolean = false,
    var illness_desc:String?=null,//患者病史描述（暂定）
    var patientRelation:Int = 0,//就诊人关系
    @SerializedName("armband_id")
    var armbandId:String? =null, //患者的臂环id
    @SerializedName("group_id")
    var groupId: String? = null,//Group id
    @SerializedName("relative_auth")
    val relativeAuth: Int=0, //1家庭组组长 2家庭组成员
    @SerializedName("is_admin")
    var isAdmin: Boolean=false //是否管理员
)
class PatientListBean{
    var count:Int = 0
    var next: String? = null
    var previous: String? = null
    var results :ArrayList<PatientModel> = arrayListOf()
}
