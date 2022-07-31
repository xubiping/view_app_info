package com.view.app.info.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import java.security.MessageDigest
import java.util.*

object Util {
    //推断应用程序是否是用户程序
    fun filterApp(pak:ApplicationInfo):Boolean{
        //原来是系统应用，用户手动升级
        /*if(info.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP !=0){
            return false
        }else */
        if ((pak.flags and ApplicationInfo.FLAG_SYSTEM) > 0) {
            //FLAG_SYSTEM >0 为系统的预安装用于
            return false
        }
        return true
    }
    fun getMD5Signature(context: Context):String{

        context?.let {
            val info = it.packageManager.getPackageInfo(it.packageName,PackageManager.GET_SIGNATURES)
            val cert = info.signatures[0].toByteArray()
            val md = MessageDigest.getInstance("MD5")
            val publicKey = md.digest(cert)
            val hexString = StringBuffer()
            for ( i in publicKey.indices){
                val appendString = Integer.toHexString(0xFF and publicKey[i].toInt()).toUpperCase(
                    Locale.US)
                if(appendString.length == 1){
                    hexString.append("0")
                }
                hexString.append(appendString)
                hexString.append(":")
            }
            return hexString.toString()
        }
        return ""
    }
    fun getSHA1Signature(context: Context):String{

            context?.let {
                val info = it.packageManager.getPackageInfo(it.packageName,PackageManager.GET_SIGNATURES)
                val cert = info.signatures[0].toByteArray()
                val md = MessageDigest.getInstance("SHA1")
                val publicKey = md.digest(cert)
                val hexString = StringBuffer()
                for ( i in publicKey.indices){
                    val appendString = Integer.toHexString(0xFF and publicKey[i].toInt()).toUpperCase(
                        Locale.US)
                    if(appendString.length == 1){
                        hexString.append("0")
                    }
                    hexString.append(appendString)
                    hexString.append(":")
                }
                return hexString.toString()
            }
       return ""
    }
    fun getSHA256Signature(context: Context):String{

        context?.let {
            val info = it.packageManager.getPackageInfo(it.packageName,PackageManager.GET_SIGNATURES)
            val cert = info.signatures[0].toByteArray()
            val md = MessageDigest.getInstance("SHA256")
            val publicKey = md.digest(cert)
            val hexString = StringBuffer()
            for ( i in publicKey.indices){
                val appendString = Integer.toHexString(0xFF and publicKey[i].toInt()).toUpperCase(
                    Locale.US)
                if(appendString.length == 1){
                    hexString.append("0")
                }
                hexString.append(appendString)
                hexString.append(":")
            }
            return hexString.toString()
        }
        return ""
    }
}