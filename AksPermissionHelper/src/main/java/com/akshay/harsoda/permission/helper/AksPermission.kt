package com.akshay.harsoda.permission.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.akshay.harsoda.permission.helper.request.PermissionRequest

/**
 * @author Akshay Harsoda
 * @since 18 Mar 2021
 */
@Suppress("unused")
object AksPermission {

    private var packageName: String? = null

    /**
     * Checks if runtime permissions are needed or not
     * @return true if android version is M or later
     */
    @JvmStatic
    fun isMarshmallowOrLater() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    /**
     *Checks if the permission is already granted
     * @return true if permission is granted
     */
    @JvmStatic
    fun isGranted(
        fContext: Context,
        fPermission: String
    ) = ActivityCompat.checkSelfPermission(fContext, fPermission) == PackageManager.PERMISSION_GRANTED

    @JvmStatic
    fun isGranted(
        fContext: Context,
        vararg fPermissions: String
    ) = fPermissions.none { permission -> !isGranted(fContext, permission) }

    @JvmStatic
    fun isGranted(
        fContext: Context,
        fPermissions: Collection<String>
    ) = fPermissions.none { permission -> !isGranted(fContext, permission) }

    @JvmStatic
    fun isGranted(
        fContext: Context,
        fPermissions: MutableSet<String>
    ) = fPermissions.none { permission -> !isGranted(fContext, permission) }


    /**
     * Checks whether you should show UI with rationale for requesting a permission
     * @return true if need a rationale for request
     */
    @JvmStatic
    fun shouldShowRationale(
        fActivity: Activity,
        fPermission: String
    ) = ActivityCompat.shouldShowRequestPermissionRationale(fActivity, fPermission)

    /**
     *Checks if the permission is permanently denied
     * @return true if permission request is show rationale
     */
    @JvmStatic
    fun isPermanentlyDenied(
        fActivity: Activity,
        fPermission: String
    ) = ActivityCompat.checkSelfPermission(fActivity, fPermission) != PackageManager.PERMISSION_GRANTED &&
            !ActivityCompat.shouldShowRequestPermissionRationale(fActivity, fPermission)

    /**
     * Creates an intent to open app settings activity, it come in handy
     * in case that permission is permanently denied and you need
     * to inform and redirect user to app settings
     * @return an intent that redirects user to app settings
     */
    @JvmStatic
    fun appDetailSettingsIntent(fContext: Context): Intent {
        return appDetailSettingsIntent(fContext.packageName)
    }

    @JvmStatic
    fun appDetailSettingsIntent(): Intent {
        return appDetailSettingsIntent(packageName!!)
    }

    @JvmStatic
    fun appDetailSettingsIntent(fPackageName: String): Intent {
        return Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", fPackageName, null)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        }
    }

    @JvmStatic
    fun with(fContext: Context): PermissionRequest {
        packageName = fContext.packageName
        return PermissionRequest(fContext)
    }
}