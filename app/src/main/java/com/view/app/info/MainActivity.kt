package com.view.app.info

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.view.app.info.adapter.AppAapter
import com.view.app.info.databinding.ActivityMainBinding
import com.view.app.info.dialog.RecycleViewDivider
import com.view.app.info.init.XAppInfo
import com.view.app.info.model.AppBean
import com.view.app.info.utils.SignUtil
import com.view.app.info.utils.Util
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var appList = ArrayList<AppBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        XAppInfo.setXFragmentManager(supportFragmentManager)
        initAppList()
        /****  不加下面两行无法线上内容 运行还会包搓 ******/
        //RecyclerView: No layout manager attached; skipping layout
        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        /**********/
        val myAdapter = AppAapter(appList)
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.addItemDecoration(RecycleViewDivider(this,LinearLayoutManager.VERTICAL))
    }

    /**
     * 获取手机里面的应用
     */
    fun initAppList(){
        val packageManager :PackageManager = packageManager

        val list :List<PackageInfo> = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES)
         if(list.isEmpty())return
        Log.v("aaeadea size",list.size.toString());
        for (packageInfo in list){
            val appBean = AppBean()
            appBean.appPackageName = packageInfo.packageName
            Log.v("aaeadea appPackageName",appBean.appPackageName);
            //val packageInfo = packageManager.getPackageInfo(appBean.appPackageName,0)
            if(Util.filterApp(packageInfo.applicationInfo)){

                appBean.icon = packageInfo.applicationInfo.loadIcon(packageManager)

                appBean.appVersionCode = packageInfo.versionCode.toString()
                appBean.appVersionName = packageInfo.versionName
                appBean.appname = packageInfo.applicationInfo.loadLabel(packageManager).toString()
                appBean.appPath = packageInfo.applicationInfo.sourceDir
               // SignUtil.getRawSignature(applicationContext,appBean.appname)
                appBean.md5 = SignUtil.getRawSignatureStr(applicationContext,appBean.appPackageName,SignUtil.SignType.MD5).toString()
                appBean.sha1 = SignUtil.getRawSignatureStr(applicationContext,appBean.appPackageName,SignUtil.SignType.SHA1).toString()
                appBean.sha256 = SignUtil.getRawSignatureStr(applicationContext,appBean.appPackageName,SignUtil.SignType.SHA256).toString()
                appBean.appfbslmy = SignUtil.getKeyHash(applicationContext,appBean.appPackageName).toString()

                appList.add(appBean)
            }

        }

    }
}