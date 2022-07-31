package com.view.app.info.dialog

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.view.app.info.R
import java.lang.Exception

abstract class BaseDialogFragment1() : DialogFragment() {

    lateinit var mContent:Context;

    abstract fun getLayoutId():Int
    abstract fun initView(view:View)

    companion object{
        const val TAG= "BaseDialogFragment"//javaClass.simpleName
        const val BASE_DIALOG_TAG = "BaseDialogFragment"
        private var mFragment:FragmentManager ?= null

    }
    init {
        arguments = Bundle()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContent = context
        Log.v(TAG,"onAttach ");
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(TAG,"onCreateView ");
        var view = inflater.inflate(getLayoutId(),container,false)
        initView(view)
        dialog?.run {
            setCancelable(true)
            setCanceledOnTouchOutside(true)
        }
        return view
    }

    override fun onStart() {
        Log.v(TAG,"onStart ");
        var dmdisplayMetrics:DisplayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(dmdisplayMetrics)
        val width = (dmdisplayMetrics.widthPixels*0.85).toInt()
        val height = (dmdisplayMetrics.heightPixels*0.8).toInt()
        dialog!!.window!!.setLayout(width,height)
        super.onStart()
    }

    override fun dismiss() {
        if(fragmentManager == null)return
        super.dismissAllowingStateLoss()
    }
    fun setFragmentManager(fragment:FragmentManager):BaseDialogFragment1{
        mFragment = fragment
        return this
    }

    fun show() {
        mFragment?.let {
            val ft = it.beginTransaction()
            try {
                val cls:Class<*> = DialogFragment::class.java
                val mDismissed = cls.getDeclaredField("mDismissed")
                mDismissed.isAccessible = true
                mDismissed[this] = false
                val mShownByMe = cls.getDeclaredField("mShownByMe")
                mShownByMe.isAccessible = true
                mShownByMe[this] = true
            }catch (e:Exception){
                super.show(ft, BASE_DIALOG_TAG)
                return
            }
            val prev = it.findFragmentByTag(BASE_DIALOG_TAG)
            if(prev !=null){
                ft.remove(prev)
            }
            ft.add(this,BASE_DIALOG_TAG)
            ft.commitAllowingStateLoss()
        }
    }


}