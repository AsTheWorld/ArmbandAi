package com.zoneyet.armbandai.utils

import java.security.MessageDigest

object MD5Util {
    fun encrypt(input: String): String {
        val md5 = MessageDigest.getInstance("MD5")
        md5.update(input.toByteArray(Charsets.UTF_8))
        val digest = md5.digest()
        val sb = StringBuilder()
        for (b in digest) {
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }
}
