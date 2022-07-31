package com.view.app.info.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.view.app.info.R


class BaseDialog() : DialogFragment() {

    companion object {
        const val BASE_DIALOG_TAG = "BASE_DIALOG"
        private var mFragment: FragmentManager? = null
        private var mBuildMethod: ((Dialog?, View?) -> Unit)? = null
        private var mDismissMethod: (() -> Unit)? = null
        private var mNegativeButtonMethod: ((Dialog?, View?) -> Unit)? = null
        private var mPositiveButtonMethod: ((Dialog?, View?) -> Unit)? = null
    }

    private var mLayoutRes: Int = 0
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mAnimRes: Int = 0
    private var mTitle: String? = null
    private var mContent: String? = null
    private var mMessage: String? = null
    private var mNegativeToDismiss = true
    private var mCanceledOnTouchOutside = false
    private var mBuildMethodAdded = false
    private var mDismissMethodAdded = false
    private var mNegativeButtonMethodAdded = false
    private var mPositiveButtonMethodAdded = false

    private val mButtonHandler =
        View.OnClickListener { view ->
            /*if (view === btn_dialog_cancel) {
                if (mNegativeToDismiss) dismiss()
                if (mNegativeButtonMethodAdded) {
                    mNegativeButtonMethod?.run {
                        invoke(dialog, getBaseView())
                    }
                }
            } else if (view === btn_dialog_ok) {
                if (mPositiveButtonMethodAdded) {
                    mPositiveButtonMethod?.run {
                        invoke(dialog, getBaseView())
                    }
                }
            }*/
        }

    init {
        arguments = Bundle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            mLayoutRes = getInt("mLayoutRes")
            mWidth = getInt("mWidth")
            mHeight = getInt("mHeight")
            mTitle = getString("mTitle")
            mContent = getString("mContent")
            mMessage = getString("mMessage")
            mNegativeToDismiss = getBoolean("mNegativeToDismiss", true)
            mCanceledOnTouchOutside = getBoolean("mCanceledOnTouchOutside")
            mBuildMethodAdded = getBoolean("mBuildMethodAdded")
            mDismissMethodAdded = getBoolean("mDismissMethodAdded")
            mNegativeButtonMethodAdded = getBoolean("mNegativeButtonMethodAdded")
            mPositiveButtonMethodAdded = getBoolean("mPositiveButtonMethodAdded")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_base, container, false)
        if (mLayoutRes > 0) {
            val contentView = inflater.inflate(mLayoutRes, null, false)
            (view.findViewById(R.id.ll_dialog_custom) as ViewGroup).addView(contentView, 0)
        }
        isCancelable = true
        dialog?.run {
            setCanceledOnTouchOutside(mCanceledOnTouchOutside)
        }
        if (mBuildMethodAdded) {
            mBuildMethod?.run {
                invoke(dialog, view)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val showTitle = !TextUtils.isEmpty(mTitle)
        val showContent = !TextUtils.isEmpty(mContent)
        val showMessage = !TextUtils.isEmpty(mMessage)
        /*if (showTitle) {
            tv_dialog_title.text = mTitle
            tv_dialog_title_only.text = mTitle
        }
        if (showContent) {
            tv_dialog_content.text = mContent
            tv_dialog_title.visibility = if (showTitle) View.VISIBLE else View.GONE
        } else {
            tv_dialog_title_only.visibility = if (showTitle) View.VISIBLE else View.GONE
        }
        if (showMessage) tv_dialog_message.text = mMessage
        tv_dialog_content.visibility = if (showContent) View.VISIBLE else View.GONE
        tv_dialog_message.visibility = if (showMessage) View.VISIBLE else View.GONE

        btn_dialog_cancel.setOnClickListener(mButtonHandler)
        btn_dialog_ok.setOnClickListener(mButtonHandler)*/
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog ?: return
        val window = dialog.window ?: return
        val params = window.attributes
        // 设置背景色透明
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // 设置Dialog动画效果
        if (mAnimRes > 0) window.setWindowAnimations(mAnimRes)
        // 设置Dialog的宽度
        if (mWidth > 0) params.width = mWidth
        // 设置Dialog的高度
        if (mHeight > 0) params.height = mHeight
        // 设置屏幕透明度 0.0f~1.0f(完全透明~完全不透明)
        params.dimAmount = 0.4f
        params.gravity = Gravity.CENTER
        window.attributes = params
    }

    override fun dismiss() {
        if (fragmentManager == null) return
        super.dismissAllowingStateLoss()
    }

    override fun onDestroy() {
        if (mDismissMethodAdded) {
            mDismissMethod?.run {
                invoke()
            }
        }
        super.onDestroy()
    }

    private fun getBaseView(): View? {
        return view
    }

    fun setFragmentManager(fragment: FragmentManager): BaseDialog {
        mFragment = fragment
        return this
    }

    fun setDialogView(@LayoutRes layoutRes: Int): BaseDialog {
        mLayoutRes = layoutRes
        arguments?.putInt("mLayoutRes", mLayoutRes)
        return this
    }

    fun setWindowSize(width: Int, height: Int): BaseDialog {
        setWidth(width)
        setHeight(height)
        return this
    }

    fun setWidth(width: Int): BaseDialog {
        mWidth = width
        arguments?.putInt("mWidth", mWidth)
        return this
    }

    fun setHeight(height: Int): BaseDialog {
        mHeight = height
        arguments?.putInt("mHeight", mHeight)
        return this
    }

    fun setAnimStyle(@StyleRes animStyle: Int): BaseDialog {
        mAnimRes = animStyle
        arguments?.putInt("mAnimRes", mAnimRes)
        return this
    }

    fun setTitle(title: String): BaseDialog {
        mTitle = title
        arguments?.putString("mTitle", mTitle)
        return this
    }

    fun setContent(content: String): BaseDialog {
        mContent = content
        arguments?.putString("mContent", mContent)
        return this
    }

    fun setMessage(message: String): BaseDialog {
        mMessage = message
        arguments?.putString("mMessage", mMessage)
        return this
    }

    fun setNegativeToDismiss(negativeToDismiss: Boolean): BaseDialog {
        mNegativeToDismiss = negativeToDismiss
        arguments?.putBoolean("mNegativeToDismiss", mNegativeToDismiss)
        return this
    }

    fun setCanceledOnTouchOutside(canceledOnTouchOutside: Boolean): BaseDialog {
        mCanceledOnTouchOutside = canceledOnTouchOutside
        arguments?.putBoolean("mCanceledOnTouchOutside", mCanceledOnTouchOutside)
        return this
    }

    fun setBuildMethod(buildMethod: (Dialog?, View?) -> Unit): BaseDialog {
        mBuildMethod = buildMethod
        arguments?.putBoolean("mBuildMethodAdded", true)
        return this
    }

    fun setDismissMethod(dismissMethod: () -> Unit): BaseDialog {
        mDismissMethod = dismissMethod
        arguments?.putBoolean("mDismissMethodAdded", true)
        return this
    }

    fun setNegativeButtonMethod(negativeButtonMethod: (Dialog?, View?) -> Unit): BaseDialog {
        mNegativeButtonMethod = negativeButtonMethod
        arguments?.putBoolean("mNegativeButtonMethodAdded", true)
        return this
    }

    fun setPositiveButtonMethod(positiveButtonMethod: (Dialog?, View?) -> Unit): BaseDialog {
        mPositiveButtonMethod = positiveButtonMethod
        arguments?.putBoolean("mPositiveButtonMethodAdded", true)
        return this
    }

    fun show() {
        mFragment?.let {
            val ft = it.beginTransaction()
            try {
                val cls: Class<*> = DialogFragment::class.java
                val mDismissed = cls.getDeclaredField("mDismissed")
                mDismissed.isAccessible = true
                mDismissed[this] = false
                val mShownByMe = cls.getDeclaredField("mShownByMe")
                mShownByMe.isAccessible = true
                mShownByMe[this] = true
            } catch (e: Exception) {
                super.show(ft, BASE_DIALOG_TAG)
                return
            }
            val prev = it.findFragmentByTag(BASE_DIALOG_TAG)
            if (prev != null) {
                ft.remove(prev)
            }
            ft.add(this, BASE_DIALOG_TAG)
            ft.commitAllowingStateLoss()
        }
    }

}