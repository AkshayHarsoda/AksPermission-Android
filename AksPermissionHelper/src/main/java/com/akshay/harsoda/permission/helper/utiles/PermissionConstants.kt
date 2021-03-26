package com.akshay.harsoda.permission.helper.utiles

import java.util.*

/**
 * @author Akshay Harsoda
 * @since 18 Mar 2021
 */
const val CODE_PERMISSION = 1313
const val EXTRA_RESULT_RECEIVER = "result_receiver"
const val EXTRA_REQUESTED_PERMISSIONS = "requested_permissions"
const val EXTRA_REQUESTED_PERMISSION_DIALOG_DATA = "requested_permission_dialog_data"
const val EXTRA_REQUESTED_PERMISSION_DIALOG_IS_SHOW = "extra_requested_permission_dialog_is_show"
const val EXTRA_REQUESTED_PERMISSION_TOAST_IS_SHOW = "extra_requested_permission_toast_is_show"
const val EXTRA_GRANTED_PERMISSIONS = "granted_permissions"
const val EXTRA_DENIED_PERMISSIONS = "denied_permissions"
const val EXTRA_PERMANENTLY_DENIED_PERMISSIONS = "permanently_denied_permissions"

private inline val String.capitalize: String
    get() {
        val strLen = this.length
        if (strLen == 0) {
            return this
        }
        val firstCodePoint = this.codePointAt(0)
        val newCodePoint = Character.toTitleCase(firstCodePoint)
        if (firstCodePoint == newCodePoint) {
            return this
        }
        val newCodePoints = IntArray(strLen) // cannot be longer than the char array
        var outOffset = 0
        newCodePoints[outOffset++] = newCodePoint // copy the first code point
        var inOffset = Character.charCount(firstCodePoint)
        while (inOffset < strLen) {
            val codePoint = this.codePointAt(inOffset)
            newCodePoints[outOffset++] = codePoint // copy the remaining ones
            inOffset += Character.charCount(codePoint)
        }
        return String(newCodePoints, 0, outOffset)
    }

fun MutableList<String>.getPermissionName(): StringBuilder {
    val permissionBuilder = StringBuilder()

    for (permission in this) {

        val arrOfStr = permission.split("\\.".toRegex()).toTypedArray()

        if (arrOfStr[arrOfStr.size - 1].equals("READ_EXTERNAL_STORAGE", ignoreCase = true)
            || arrOfStr[arrOfStr.size - 1].equals("WRITE_EXTERNAL_STORAGE", ignoreCase = true)
        ) {

            if (!permissionBuilder.toString().contains("Storage")) {

                permissionBuilder.append("Storage")
                permissionBuilder.append(", ")
            }

        } else {

            if (arrOfStr[arrOfStr.size - 1].contains("_")) {

                val arrOfStr2 = arrOfStr[arrOfStr.size - 1].split("_".toRegex()).toTypedArray()

                for (value in arrOfStr2) {
                    permissionBuilder.append(value.toLowerCase(Locale.getDefault()).capitalize)
                    permissionBuilder.append(" ")
                }

                permissionBuilder.deleteCharAt(permissionBuilder.length - 1)
                permissionBuilder.append(", ")

            } else {

                permissionBuilder.append(arrOfStr[arrOfStr.size - 1].toLowerCase(Locale.getDefault()).capitalize)
                permissionBuilder.append(", ")
            }
        }
    }

    permissionBuilder.deleteCharAt(permissionBuilder.length - 1)
    permissionBuilder.deleteCharAt(permissionBuilder.length - 1)

    return permissionBuilder
}