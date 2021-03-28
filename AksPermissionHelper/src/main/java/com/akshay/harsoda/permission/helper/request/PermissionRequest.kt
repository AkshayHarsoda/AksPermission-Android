package com.akshay.harsoda.permission.helper.request

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import com.akshay.harsoda.permission.helper.AksPermission
import com.akshay.harsoda.permission.helper.AksPermission.isGranted
import com.akshay.harsoda.permission.helper.R
import com.akshay.harsoda.permission.helper.activity.PermissionActivity
import com.akshay.harsoda.permission.helper.callBack.PermissionResultListener
import com.akshay.harsoda.permission.helper.utiles.*
import java.io.Serializable

/**
 * @author Akshay Harsoda
 * @since 18 Mar 2021
 */
@Suppress("unused")
open class PermissionRequest(private val mContext: Activity) : Serializable {

    private val TAG = javaClass.simpleName

    private val mPermissions = mutableSetOf<String>()
    private var mSettingDialog: SettingDialogRequest = SettingDialogRequest()
    private var mDialog: Dialog? = null
    private var mIsNeedToShowSettingDialog: Boolean = true
    private var mIsSkipAutoAskPermission: Boolean = false

    //<editor-fold desc="set Permissions list">
    @JvmName("permissions")
    @NonNull
    fun permissions(fPermission: String) = this@PermissionRequest.apply {
        this.mPermissions.clear()
        this.mPermissions.add(fPermission)
    }

    @JvmName("permissions")
    @NonNull
    fun permissions(vararg fPermissions: String) = this@PermissionRequest.apply {
        this.mPermissions.clear()
        this.mPermissions.addAll(fPermissions)
    }

    @JvmName("permissions")
    @NonNull
    fun permissions(fPermissions: Collection<String>) = this@PermissionRequest.apply {
        this.mPermissions.clear()
        this.mPermissions.addAll(fPermissions)
    }

    @JvmName("permissions")
    @NonNull
    fun permissions(fPermissions: MutableSet<String>) = this@PermissionRequest.apply {
        this.mPermissions.clear()
        this.mPermissions.addAll(fPermissions)
    }
    //</editor-fold>

    @JvmName("skipAutoAskPermission")
    @NonNull
    fun skipAutoAskPermission() = this@PermissionRequest.apply {
        this.mIsSkipAutoAskPermission = true
    }

    //<editor-fold desc="Setting Dialog">
    @JvmName("isShowDefaultSettingDialog")
    @NonNull
    fun isShowDefaultSettingDialog(fIsShow: Boolean) = this@PermissionRequest.apply {
        this.mIsNeedToShowSettingDialog = fIsShow
    }

    @JvmName("setDefaultSettingDialog")
    @NonNull
    fun setDefaultSettingDialog(fSettingDialog: SettingDialogRequest) =
        this@PermissionRequest.apply {
            this.mSettingDialog = fSettingDialog
        }

    @JvmName("setCustomSettingDialog")
    @NonNull
    fun setCustomSettingDialog(fDialog: Dialog) = this@PermissionRequest.apply {
        this.mIsNeedToShowSettingDialog = false
        this.mDialog = fDialog
    }
    //</editor-fold>

    //<editor-fold desc="Request Permission">
    @JvmName("request")
    fun request(
        onGrantedResult: (fGrantedList: Set<String>) -> Unit
    ) {
        request(object : PermissionResultListener() {
            override fun onGrantedResult(fGrantedList: Set<String>) {
                onGrantedResult.invoke(fGrantedList)
            }
        })
    }

    @JvmName("request")
    fun request(
        onGrantedResult: (fGrantedList: Set<String>) -> Unit,
        onDeniedResult: (fDeniedList: Set<String>) -> Unit,
        onPermanentlyDeniedResult: (fPermanentlyDeniedList: Set<String>) -> Unit
    ) {
        request(object : PermissionResultListener() {
            override fun onGrantedResult(fGrantedList: Set<String>) {
                onGrantedResult.invoke(fGrantedList)
            }

            override fun onDeniedResult(fDeniedList: Set<String>) {
                super.onDeniedResult(fDeniedList)
                onDeniedResult.invoke(fDeniedList)
            }

            override fun onPermanentlyDeniedResult(fPermanentlyDeniedList: Set<String>) {
                super.onPermanentlyDeniedResult(fPermanentlyDeniedList)
                onPermanentlyDeniedResult.invoke(fPermanentlyDeniedList)
            }
        })
    }

    @JvmName("request")
    fun request(callback: PermissionResultListener) {

        mResultListener = object : PermissionResultListener() {
            override fun onGrantedResult(fGrantedList: Set<String>) {
                Log.i(TAG, "onGrantedResult: GrantedList -> $fGrantedList")
                callback.onGrantedResult(fGrantedList = fGrantedList)
            }

            override fun onDeniedResult(fDeniedList: Set<String>) {
                super.onDeniedResult(fDeniedList)
                Log.i(TAG, "onDeniedResult: DeniedList -> $fDeniedList")
                callback.onDeniedResult(fDeniedList = fDeniedList)
            }

            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            override fun onPermanentlyDeniedResult(fPermanentlyDeniedList: Set<String>) {
                super.onPermanentlyDeniedResult(fPermanentlyDeniedList)
                Log.i(TAG, "onPermanentlyDeniedResult: PermanentlyDeniedList -> $fPermanentlyDeniedList")
                mContext.apply {
                    if (!isFinishing && !isDestroyed) {
                        mDialog?.let {
                            if (!it.isShowing) {
                                it.show()
                            }
                        }

                        if (mIsNeedToShowSettingDialog) {
                            doNotAskAgain(
                                fPermanentlyDeniedList.toMutableList().getPermissionName().toString()
                            )
                        }
                    }
                }
                callback.onPermanentlyDeniedResult(fPermanentlyDeniedList = fPermanentlyDeniedList)
            }
        }

        if (isGranted(mContext, mPermissions)) {
            mResultListener?.onGrantedResult(mPermissions.toSet())
        } else {
            callPermissionActivity()
        }
    }
    //</editor-fold>

    private fun callPermissionActivity() {

        val intent = Intent(mContext, PermissionActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .putExtras(
                bundleOf(
                    EXTRA_REQUESTED_PERMISSIONS to mPermissions.distinct().toTypedArray(),
                    IS_SKIP_AUTO_ASK_PERMISSION to mIsSkipAutoAskPermission
                )
            )
        mContext.startActivity(intent)
    }

    private fun doNotAskAgain(fMessage: String) {

        mContext.showAlert(
            fTitle = this.mSettingDialog.getDialogTitle()
                ?: mContext.resources.getString(R.string.dialog_title),
            fMessage = this.mSettingDialog.getDialogMessage()
                ?: mContext.resources.getString(R.string.dialog_messages, fMessage),
            fPositiveText = this.mSettingDialog.getDialogPositiveText()
                ?: mContext.resources.getString(android.R.string.ok),
            fNegativeText = this.mSettingDialog.getDialogNegativeText()
                ?: mContext.resources.getString(android.R.string.cancel),
            fTitleColor = this.mSettingDialog.getDialogTitleColor() ?: Color.BLACK,
            fMessageColor = this.mSettingDialog.getDialogMessageColor() ?: Color.BLACK,
            fPositiveColor = this.mSettingDialog.getDialogPositiveColor() ?: Color.BLACK,
            fNegativeColor = this.mSettingDialog.getDialogNegativeColor() ?: Color.BLACK,
            fTitleTypeface = this.mSettingDialog.getDialogTitleTypeface(),
            fMessageTypeface = this.mSettingDialog.getDialogMessageTypeface(),
            fPositiveTypeface = this.mSettingDialog.getDialogPositiveTypeface(),
            fNeutralTypeface = this.mSettingDialog.getDialogNegativeTypeface(),
            fButtonClickListener = object : OnAlertButtonClickListener {
                override fun onPositiveButtonClick() {
                    mContext.packageName?.let {
                        mContext.startActivity(AksPermission.appDetailSettingsIntent(it))
                    }
                }
            }
        )
    }
}