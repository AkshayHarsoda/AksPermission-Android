package com.akshay.harsoda.permission.helper.activity

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.akshay.harsoda.permission.helper.*
import com.akshay.harsoda.permission.helper.AksPermission.isGranted
import com.akshay.harsoda.permission.helper.utiles.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Akshay Harsoda
 * @since 18 Mar 2021
 */
internal class PermissionActivity : AppCompatActivity() {

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

    //<editor-fold desc="get intent data">
    private fun getRequestedPermissions() = intent.getStringArrayExtra(EXTRA_REQUESTED_PERMISSIONS)

    private fun isSkipAutoAskPermission() =
        intent.getBooleanExtra(IS_SKIP_AUTO_ASK_PERMISSION, false)
    //</editor-fold>

    //<editor-fold desc="Check Permissions are Granted or Denied">
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
    //</editor-fold>

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
                if (isSkipAutoAskPermission()) {
                    mResultListener?.onDeniedResult(deniedList.toSet())
                    finish()
                } else {
                    checkAndAskPermission()
                    mResultListener?.onDeniedResult(deniedList.toSet())
                }
            }

            isPermanentlyDenied -> {
                mResultListener?.onPermanentlyDeniedResult(permanentlyDeniedList.toSet())
                finish()
            }

            isGranted -> {
                mResultListener?.onGrantedResult(grantedList.toSet())
                finish()
            }
        }
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