package com.view.app.info.test

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object BaseDialogFragment {
    /**
     * 获取应用签名
     *
     * @param context
     * @param packageName
     * @return
     */
    fun getRawSignatureStr(context: Context, packageName: String?): String? {
        try {
            val signs =
                getRawSignature(context, packageName)
            return getSignValidString(signs!![0].toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
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

    @Throws(NoSuchAlgorithmException::class)
    private fun getSignValidString(paramArrayOfByte: ByteArray): String? {
        val localMessageDigest = MessageDigest.getInstance("MD5")
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
            )
            if (str.length == 1) {
                str = "0$str"
            }
            localStringBuilder.append(str)
            i++
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