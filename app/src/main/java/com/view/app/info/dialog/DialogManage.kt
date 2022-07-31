package com.view.app.info.dialog

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.view.app.info.model.AppBean

object DialogManage {
    val TAG= "DialogManage"//javaClass.simpleName
    fun enterAppInfo(fm: FragmentManager, appBean: AppBean){
        try {

            fm?.let {
                AppInfoDialog()
                    .setAB(appBean)
                    .setFragmentManager(fm)
                    .show()

            }

            /*fm?.let {
                BaseDialog()
                    .setFragmentManager(fm)
                    .setMessage("确定删除？")
                    .setCanceledOnTouchOutside(true)
                    .setPositiveButtonMethod { dialog, view ->
                        dialog?.dismiss()
                    }
                    .show()
            }*/

            /*fm?.let {
                val ft = it.beginTransaction()
                val prev = it.findFragmentByTag("enterAppInfo")
                if(prev !=null){
                    ft.remove(prev)
                }
                val appInfoDialog = AppInfoDialog()
                appInfoDialog.initData(appBean)
                ft.add(appInfoDialog,"enterAppInfo")
                ft.commitAllowingStateLoss()
            }*/

        }catch (throwable:Throwable){

        }
    }
    /*fun enterAppInfo1(fm: FragmentManager, appBean: AppBean){
        try {
            if (fm == null)return
            AppInfoDialog()
                .setF

        }catch (throwable:Throwable){

        }
    }*/
}