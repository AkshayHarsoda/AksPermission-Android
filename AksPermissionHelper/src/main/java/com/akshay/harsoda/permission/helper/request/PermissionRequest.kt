package com.akshay.harsoda.permission.helper.request

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import com.akshay.harsoda.permission.helper.AksPermission.isGranted
import com.akshay.harsoda.permission.helper.R
import com.akshay.harsoda.permission.helper.activity.PermissionActivity
import com.akshay.harsoda.permission.helper.utiles.*
import java.io.Serializable

/**
 * @author Akshay Harsoda
 * @since 18 Mar 2021
 */
@Suppress("unused")
open class PermissionRequest(private val mContext: Context): Serializable {

    private val mPermissions = mutableSetOf<String>()
    private var mSettingDialog: SettingDialogRequest = SettingDialogRequest()
    private var mIsNeedToShowSettingDialog: Boolean = true
    private var mIsNeedToShowToast: Boolean = false

    @JvmName("permission")
    @NonNull
    fun permission(fPermission: String): PermissionRequest {
        this.mPermissions.clear()
        this.mPermissions.add(fPermission)
        return this
    }

    @JvmName("permissionList")
    @NonNull
    fun permissionList(vararg fPermissions: String): PermissionRequest {
        this.mPermissions.clear()
        this.mPermissions.addAll(fPermissions)
        return this
    }

    @JvmName("permissionList")
    @NonNull
    fun permissionList(fPermissions: Collection<String>): PermissionRequest {
        this.mPermissions.clear()
        this.mPermissions.addAll(fPermissions)
        return this
    }

    @JvmName("setCustomSettingDialog")
    @NonNull
    fun setCustomSettingDialog(fSettingDialog: SettingDialogRequest): PermissionRequest {
        this.mSettingDialog = fSettingDialog
        return this
    }

    @JvmName("isShowDefaultSettingDialog")
    @NonNull
    fun isShowDefaultSettingDialog(fIsShow: Boolean): PermissionRequest {
        this.mIsNeedToShowSettingDialog = fIsShow
        return this
    }

    @JvmName("isShowDefaultToast")
    @NonNull
    fun isShowDefaultToast(fIsShow: Boolean): PermissionRequest {
        this.mIsNeedToShowToast = fIsShow
        return this
    }

    @JvmName("request")
    fun request(
        listener: (
            grantedList: Set<String>,
            deniedList: Set<String>,
            permanentlyDeniedList: Set<String>
        ) -> Unit
    ) {

        if (isGranted(mContext, mPermissions)) {
            //Already have all permissions, no need to start activity
            if (mIsNeedToShowToast) {
                Toast.makeText(
                    mContext,
                    mContext.resources.getString(
                        R.string.granted_permission_toast_message,
                        mPermissions.toMutableList().getPermissionName().toString()
                    ),
                    Toast.LENGTH_SHORT
                ).show()
            }
            listener.invoke(mPermissions.toSet(), setOf(), setOf())
        } else {
            callPermissionActivity(PermissionResult(listener))
        }
    }

    @JvmName("request")
    fun request(
        listener: (
            grantedList: Set<String>
        ) -> Unit
    ) {
        request { grantedList, _, _ ->
            listener.invoke(grantedList)
        }
    }

    private fun callPermissionActivity(fPermissionResult: PermissionResult) {

        val intent = Intent(mContext, PermissionActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .putExtras(
                bundleOf(
                    EXTRA_REQUESTED_PERMISSIONS to mPermissions.distinct().toTypedArray(),
                    EXTRA_RESULT_RECEIVER to fPermissionResult,
                    EXTRA_REQUESTED_PERMISSION_DIALOG_DATA to mSettingDialog,
                    EXTRA_REQUESTED_PERMISSION_DIALOG_IS_SHOW to mIsNeedToShowSettingDialog,
                    EXTRA_REQUESTED_PERMISSION_TOAST_IS_SHOW to mIsNeedToShowToast
                )
            )
        mContext.startActivity(intent)
    }
}