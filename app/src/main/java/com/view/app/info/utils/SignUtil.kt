package com.view.app.info.utils

import android.content.Context
import com.view.app.info.test.BaseDialogFragment
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.util.Base64
import android.util.Log
import java.lang.Exception
import java.lang.StringBuilder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.Throws

object SignUtil {

    enum class SignType{
        MD5,
        SHA1,
        SHA256
    }
    /**
     * 获取应用签名
     *
     * @param context
     * @param packageName
     * @return
     */
    fun getRawSignatureStr(context: Context, packageName: String?,type:SignType): String? {
        try {
            val signs =
                getRawSignature(context, packageName)
            return getSignValidString(signs!![0].toByteArray(),type)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getRawSignature(context: Context, packageName: String?): Array<Signature>? {
        if (packageName == null || packageName.length == 0) {
            return null
        }
        try {
            val info =
                context.packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            if (info != null) {
                return info.signatures
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getSignValidString(paramArrayOfByte: ByteArray,type:SignType): String? {
        val localMessageDigest = MessageDigest.getInstance(type.toString())
        localMessageDigest.update(paramArrayOfByte)
        return toHexString(localMessageDigest.digest())
    }

    private fun toHexString(paramArrayOfByte: ByteArray?): String? {
        if (paramArrayOfByte == null) {
            return null
        }
        val localStringBuilder = StringBuilder(2 * paramArrayOfByte.size)
        var i = 0
        while (true) {
            if (i >= paramArrayOfByte.size) {
                return localStringBuilder.toString()
            }
            var str = Integer.toString(
                0xFF and paramArrayOfByte[i]
                    .toInt(), 16
            ).toUpperCase(
                Locale.US)
            if (str.length == 1) {
                str = "0$str"
            }
            localStringBuilder.append(str)
            i++
            if (i < paramArrayOfByte.size) {
                localStringBuilder.append(":")
            }


        }
    }

    fun getKeyHash(context: Context, packageNmae: String): String? {
        var keyHash = ""
        try {
            Log.d("KeyHash", "packageNmae=$packageNmae")
            val info = context.packageManager.getPackageInfo(
                packageNmae,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT)
                Log.d("KeyHash:", "KeyHash是$keyHash")
                return keyHash
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
        return ""
    }
}