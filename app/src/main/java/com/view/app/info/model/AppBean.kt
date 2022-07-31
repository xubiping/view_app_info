package com.view.app.info.model

import android.graphics.drawable.Drawable
import android.widget.TextView
import com.view.app.info.R

class AppBean {
    lateinit var icon:Drawable
    var appname:String = ""
    var appPath:String = ""
    var appVersionCode:String="";
    var appVersionName:String="";
    var appPackageName:String="";
    var appfbslmy:String="";
    var md5:String="";
    var sha1:String="";
    var sha256:String="";

}