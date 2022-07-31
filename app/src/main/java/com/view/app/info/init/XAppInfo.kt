package com.view.app.info.init

import androidx.fragment.app.FragmentManager

object XAppInfo {
    lateinit var fragmentManager: FragmentManager
    fun getXFragmentManager(): FragmentManager {
        return fragmentManager
    }

    fun setXFragmentManager(fm:FragmentManager) {
         fragmentManager =fm
    }



}