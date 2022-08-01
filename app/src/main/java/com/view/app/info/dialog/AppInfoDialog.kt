package com.view.app.info.dialog

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.view.app.info.R
import com.view.app.info.model.AppBean
import com.view.app.info.utils.Util

class AppInfoDialog : BaseDialogFragment1() {
   // override val TAG= "AppInfoDialog"//javaClass.simpleName
    lateinit var  iv_app_icon:ImageView
    lateinit var tv_app_name :TextView
    lateinit var tv_app_packagename :TextView
    lateinit var tv_app_versioncode :TextView
    lateinit var tv_app_versionname :TextView
    lateinit var tv_app_path :TextView
    lateinit var tv_app_fbslmy :TextView
    lateinit var tv_app_md5 :TextView
    lateinit var tv_app_sha1 :TextView
    lateinit var tv_app_sha256 :TextView
    lateinit var appBean:AppBean;
    override fun getLayoutId(): Int {
        Log.v(DialogManage.TAG,"getLayoutId ");
        return R.layout.app_info
    }

    override fun initView(view: View) {
        Log.v(DialogManage.TAG,"initView ");
         iv_app_icon = view.findViewById<ImageView>(R.id.iv_app_icon)
         tv_app_name = view.findViewById<TextView>(R.id.tv_app_name)
         tv_app_packagename = view.findViewById<TextView>(R.id.tv_app_packagename)
         tv_app_versioncode = view.findViewById<TextView>(R.id.tv_app_versioncode)
         tv_app_versionname = view.findViewById<TextView>(R.id.tv_app_versionname)
         tv_app_path = view.findViewById<TextView>(R.id.tv_app_path)
         tv_app_fbslmy = view.findViewById<TextView>(R.id.tv_app_fbslmy)
         tv_app_md5 = view.findViewById<TextView>(R.id.tv_app_md5)
         tv_app_sha1 = view.findViewById<TextView>(R.id.tv_app_sha1)
         tv_app_sha256 = view.findViewById<TextView>(R.id.tv_app_sha256)
        textCopy(tv_app_name)
        textCopy(tv_app_packagename)
        textCopy(tv_app_versioncode)
        textCopy(tv_app_versionname)
        textCopy(tv_app_path)
        textCopy(tv_app_fbslmy)
        textCopy(tv_app_md5)
        textCopy(tv_app_sha1)
        textCopy(tv_app_sha256)
        initData()

    }
    fun setAB(ab:AppBean):AppInfoDialog{
        appBean = ab
        return this
    }
    fun initData(){
        Log.v(DialogManage.TAG,"initData ");
        iv_app_icon.setImageDrawable(appBean.icon)
        tv_app_name.text = appBean.appname
        tv_app_packagename.text = appBean.appPackageName
        tv_app_versioncode.text = appBean.appVersionCode
        tv_app_versionname.text = appBean.appVersionName
        tv_app_path.text = appBean.appPath
        tv_app_fbslmy.text = appBean.appfbslmy
        tv_app_md5.text = appBean.md5
        tv_app_sha1.text = appBean.sha1
        tv_app_sha256.text = appBean.sha256
    }
    fun textCopy(view: TextView){
        if(view == null){
            Util.toast(mContent,"text null")
            return
        }
        view.setOnClickListener {
            Util.copy(mContent, view.text as String)
        }
    }

}