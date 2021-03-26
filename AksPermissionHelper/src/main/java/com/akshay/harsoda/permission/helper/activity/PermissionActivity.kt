package com.akshay.harsoda.permission.helper.activity

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import com.akshay.harsoda.permission.helper.*
import com.akshay.harsoda.permission.helper.AksPermission.appDetailSettingsIntent
import com.akshay.harsoda.permission.helper.AksPermission.isGranted
import com.akshay.harsoda.permission.helper.request.SettingDialogRequest
import com.akshay.harsoda.permission.helper.utiles.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Akshay Harsoda
 * @since 18 Mar 2021
 */
internal class PermissionActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    private val mActivity: PermissionActivity = this@PermissionActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkAndAskPermission()
    }

    private fun checkAndAskPermission() {

        if (getDeniedPermissions().isNotEmpty()) {
            ActivityCompat.requestPermissions(
                mActivity,
                getDeniedPermissions(),
                CODE_PERMISSION
            )
        } else {
            sendResult(ArrayList(getGrantedPermissions().toList()), ArrayList(), ArrayList())
        }
    }

    private fun isNeedToShowSettingDialog() =
        intent.getBooleanExtra(EXTRA_REQUESTED_PERMISSION_DIALOG_IS_SHOW, true)

    private fun isNeedToShowToast() =
        intent.getBooleanExtra(EXTRA_REQUESTED_PERMISSION_TOAST_IS_SHOW, false)

    private fun getRequestedPermissions() = intent.getStringArrayExtra(EXTRA_REQUESTED_PERMISSIONS)

    private fun getDeniedPermissions() = getRequestedPermissions()!!.filter { lPermission ->
        !isGranted(
            mActivity,
            lPermission
        )
    }.toTypedArray()

    private fun getGrantedPermissions() = getRequestedPermissions()!!.filter { lPermission ->
        isGranted(
            mActivity,
            lPermission
        )
    }.toTypedArray()

    private fun getSettingDialogData() = intent.getSerializableExtra(
        EXTRA_REQUESTED_PERMISSION_DIALOG_DATA
    ) as? SettingDialogRequest

    private fun sendResult(
        grantedList: ArrayList<String>,
        deniedList: ArrayList<String>,
        permanentlyDeniedList: ArrayList<String>
    ) {

        val isGranted = grantedList.isNotEmpty()
        val isDenied = deniedList.isNotEmpty()
        val isPermanentlyDenied = permanentlyDeniedList.isNotEmpty()

        when {
            isDenied -> {
                Log.i(TAG, "sendResult: deniedList: $deniedList")
                if (isNeedToShowToast()) {
                    Toast.makeText(
                        mActivity,
                        mActivity.resources.getString(
                            R.string.denied_permission_toast_message,
                            deniedList.getPermissionName().toString()
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                checkAndAskPermission()
            }

            isPermanentlyDenied -> {
                Log.i(TAG, "sendResult: permanentlyDeniedList: $permanentlyDeniedList")
                if (isNeedToShowToast()) {
                    Toast.makeText(
                        mActivity,
                        mActivity.resources.getString(
                            R.string.permanentlyDenied_permission_toast_message,
                            permanentlyDeniedList.getPermissionName().toString()
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if (isNeedToShowSettingDialog()) {
                    doNotAskAgain(
                        permanentlyDeniedList.getPermissionName().toString()
                    )
                }
            }

            isGranted -> {
                Log.i(TAG, "sendResult: grantedList: $grantedList")
                if (isNeedToShowToast()) {
                    Toast.makeText(
                        mActivity,
                        mActivity.resources.getString(
                            R.string.granted_permission_toast_message,
                            grantedList.getPermissionName().toString()
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                val resultReceiver =
                    intent.getParcelableExtra<ResultReceiver>(EXTRA_RESULT_RECEIVER)

                resultReceiver?.send(
                    CODE_PERMISSION,
                    bundleOf(
                        EXTRA_GRANTED_PERMISSIONS to grantedList,
                        EXTRA_DENIED_PERMISSIONS to deniedList,
                        EXTRA_PERMANENTLY_DENIED_PERMISSIONS to permanentlyDeniedList
                    )
                )

                finish()
            }
        }
    }

    private fun doNotAskAgain(fMessage: String) {

        mActivity.showAlert(
            fTitle = getSettingDialogData()?.getDialogTitle()
                ?: mActivity.resources.getString(R.string.dialog_title),
            fMessage = getSettingDialogData()?.getDialogMessage()
                ?: mActivity.resources.getString(R.string.dialog_messages, fMessage),
            fPositiveText = getSettingDialogData()?.getDialogPositiveText()
                ?: mActivity.resources.getString(android.R.string.ok),
            fNegativeText = getSettingDialogData()?.getDialogNegativeText()
                ?: mActivity.resources.getString(android.R.string.cancel),
            fTitleColor = getSettingDialogData()?.getDialogTitleColor() ?: Color.BLACK,
            fMessageColor = getSettingDialogData()?.getDialogMessageColor() ?: Color.BLACK,
            fPositiveColor = getSettingDialogData()?.getDialogPositiveColor() ?: Color.BLACK,
            fNegativeColor = getSettingDialogData()?.getDialogNegativeColor() ?: Color.BLACK,
            fTitleTypeface = getSettingDialogData()?.getDialogTitleTypeface(),
            fMessageTypeface = getSettingDialogData()?.getDialogMessageTypeface(),
            fPositiveTypeface = getSettingDialogData()?.getDialogPositiveTypeface(),
            fNeutralTypeface = getSettingDialogData()?.getDialogNegativeTypeface(),
            fButtonClickListener = object : OnAlertButtonClickListener {
                override fun onPositiveButtonClick() {
                    mActivity.startActivity(appDetailSettingsIntent())
                }
            }
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val granted = ArrayList<String>()
        granted.addAll(getGrantedPermissions())

        val denied = ArrayList<String>()
        val permanentlyDenied = ArrayList<String>()

        permissions.forEachIndexed { index, permission ->

            if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                granted += permission
            } else {

                if (!ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
                    permanentlyDenied += permission
                } else {
                    denied += permission
                }
            }
        }

        sendResult(granted, denied, permanentlyDenied)
    }
}