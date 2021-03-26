package com.akshay.harsoda.permission.helper.request

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import com.akshay.harsoda.permission.helper.utiles.EXTRA_DENIED_PERMISSIONS
import com.akshay.harsoda.permission.helper.utiles.EXTRA_GRANTED_PERMISSIONS
import com.akshay.harsoda.permission.helper.utiles.EXTRA_PERMANENTLY_DENIED_PERMISSIONS

/**
 * @author Akshay Harsoda
 * @since 18 Mar 2021
 */
internal class PermissionResult(private val mListener: (Set<String>, Set<String>, Set<String>) -> Unit) :
    ResultReceiver(Handler(Looper.getMainLooper())) {

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        super.onReceiveResult(resultCode, resultData)

        var granted = ArrayList<String>()
        var denied = ArrayList<String>()
        var permanentlyDenied = ArrayList<String>()

        resultData?.apply {
            granted = getStringArrayList(EXTRA_GRANTED_PERMISSIONS) ?: ArrayList()
            denied = getStringArrayList(EXTRA_DENIED_PERMISSIONS) ?: ArrayList()
            permanentlyDenied =
                getStringArrayList(EXTRA_PERMANENTLY_DENIED_PERMISSIONS) ?: ArrayList()
        }

        mListener.invoke(granted.toSet(), denied.toSet(), permanentlyDenied.toSet())
    }
}