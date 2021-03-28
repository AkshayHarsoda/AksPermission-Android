package com.akshay.harsoda.permission.helper.callBack

abstract class PermissionResultListener {
    abstract fun onGrantedResult(fGrantedList: Set<String>)
    open fun onDeniedResult(fDeniedList: Set<String>) {}
    open fun onPermanentlyDeniedResult(fPermanentlyDeniedList: Set<String>) {}
}