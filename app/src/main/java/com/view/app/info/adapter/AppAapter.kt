package com.view.app.info.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.view.app.info.R
import com.view.app.info.dialog.DialogManage
import com.view.app.info.init.XAppInfo
import com.view.app.info.model.AppBean

class AppAapter(val applist:List<AppBean>) : RecyclerView.Adapter<AppAapter.MyViewHolder>() {
    val TAG= "AppAapter"//javaClass.simpleName

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_app_icon = itemView.findViewById<ImageView>(R.id.iv_app_icon)
        val tv_app_name = itemView.findViewById<TextView>(R.id.tv_app_name)
        val tv_app_versioncode = itemView.findViewById<TextView>(R.id.tv_app_versioncode)
        val tv_app_versionname = itemView.findViewById<TextView>(R.id.tv_app_versionname)
        val tv_app_packagename = itemView.findViewById<TextView>(R.id.tv_app_packagename)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.v(TAG,"onCreateViewHolder");
        val view = LayoutInflater.from(parent.context).inflate(R.layout.app_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.v(TAG,"onBindViewHolder");
        val appInfo = applist[position]
        with(holder){
            iv_app_icon.setImageDrawable(appInfo.icon)
            tv_app_name.text = appInfo.appname
            tv_app_versioncode.text = "versionCode:"+appInfo.appVersionCode
            tv_app_versionname.text = "versionName:"+appInfo.appVersionName
            tv_app_packagename.text = appInfo.appPackageName
            Log.v(TAG,appInfo.appPackageName);
            iv_app_icon.setOnClickListener {
                Log.v(TAG,"iv_app_icon setOnClickListener");
                DialogManage.enterAppInfo(XAppInfo.getXFragmentManager(),appInfo)
            }
        }
    }

    override fun getItemCount() = applist.size


}