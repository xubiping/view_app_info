package com.view.app.info.utils

import android.content.ClipboardManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.view.Gravity
import android.widget.Toast
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
    fun copy (context: Context,str:String){
        try {
            val cm:ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.text = str;
            toast(context,str)
        }catch (e:Throwable){

        }
    }
    fun toast(context: Context,str:String){
        val toast = Toast.makeText(context,str,Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP,0,200)
        toast.show()
    }
}