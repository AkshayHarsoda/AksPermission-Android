package com.akshay.harsoda.permission.helper.request

import android.graphics.Typeface
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import java.io.Serializable

/**
 * @author Akshay Harsoda
 * @since 18 Mar 2021
 */
class SettingDialogRequest : Serializable {

    private var mTitle: String? = null
    private var mMessage: String? = null
    private var mPositiveText: String? = null
    private var mNegativeText: String? = null
    @ColorInt
    private var mTitleColor: Int? = null
    @ColorInt
    private var mMessageColor: Int? = null
    @ColorInt
    private var mPositiveColor: Int? = null
    @ColorInt
    private var mNegativeColor: Int? = null
    private var mTitleTypeface: Typeface? = null
    private var mMessageTypeface: Typeface? = null
    private var mPositiveTypeface: Typeface? = null
    private var mNegativeTypeface: Typeface? = null

    @JvmName("setDialogTitle")
    @NonNull
    fun setDialogTitle(fTitle: String): SettingDialogRequest {
        this.mTitle = fTitle
        return this
    }

    @JvmName("setDialogMessage")
    @NonNull
    fun setDialogMessage(fMessage: String): SettingDialogRequest {
        this.mMessage = fMessage
        return this
    }

    @JvmName("setDialogPositiveText")
    @NonNull
    fun setDialogPositiveText(fPositiveText: String): SettingDialogRequest {
        this.mPositiveText = fPositiveText
        return this
    }

    @JvmName("setDialogNegativeText")
    @NonNull
    fun setDialogNegativeText(fNegativeText: String): SettingDialogRequest {
        this.mNegativeText = fNegativeText
        return this
    }

    @JvmName("setDialogTitleColor")
    @NonNull
    fun setDialogTitleColor(@ColorInt fTitleColor: Int): SettingDialogRequest {
        this.mTitleColor = fTitleColor
        return this
    }

    @JvmName("setDialogMessageColor")
    @NonNull
    fun setDialogMessageColor(@ColorInt fMessageColor: Int): SettingDialogRequest {
        this.mMessageColor = fMessageColor
        return this
    }

    @JvmName("setDialogPositiveColor")
    @NonNull
    fun setDialogPositiveColor(@ColorInt fPositiveColor: Int): SettingDialogRequest {
        this.mPositiveColor = fPositiveColor
        return this
    }

    @JvmName("setDialogNegativeColor")
    @NonNull
    fun setDialogNegativeColor(@ColorInt fNegativeColor: Int): SettingDialogRequest {
        this.mNegativeColor = fNegativeColor
        return this
    }

    @JvmName("setDialogTitleTypeface")
    @NonNull
    fun setDialogTitleTypeface(fTitleTypeface: Typeface): SettingDialogRequest {
        this.mTitleTypeface = fTitleTypeface
        return this
    }

    @JvmName("setDialogMessageTypeface")
    @NonNull
    fun setDialogMessageTypeface(fMessageTypeface: Typeface): SettingDialogRequest {
        this.mMessageTypeface = fMessageTypeface
        return this
    }

    @JvmName("setDialogPositiveTypeface")
    @NonNull
    fun setDialogPositiveTypeface(fPositiveTypeface: Typeface): SettingDialogRequest {
        this.mPositiveTypeface = fPositiveTypeface
        return this
    }

    @JvmName("setDialogNegativeTypeface")
    @NonNull
    fun setDialogNegativeTypeface(fNegativeTypeface: Typeface): SettingDialogRequest {
        this.mNegativeTypeface = fNegativeTypeface
        return this
    }

    @JvmName("getDialogTitle")
    fun getDialogTitle(): String? {
        return this.mTitle
    }

    @JvmName("getDialogMessage")
    fun getDialogMessage(): String? {
        return this.mMessage
    }

    @JvmName("getDialogPositiveText")
    fun getDialogPositiveText(): String? {
        return this.mPositiveText
    }

    @JvmName("getDialogNegativeText")
    fun getDialogNegativeText(): String? {
        return this.mNegativeText
    }

    @JvmName("getDialogTitleColor")
    @ColorInt
    fun getDialogTitleColor(): Int? {
        return this.mTitleColor
    }

    @JvmName("getDialogMessageColor")
    @ColorInt
    fun getDialogMessageColor(): Int? {
        return this.mMessageColor
    }

    @JvmName("getDialogPositiveColor")
    @ColorInt
    fun getDialogPositiveColor(): Int? {
        return this.mPositiveColor
    }

    @JvmName("getDialogNegativeColor")
    @ColorInt
    fun getDialogNegativeColor(): Int? {
        return this.mNegativeColor
    }

    @JvmName("getDialogTitleTypeface")
    fun getDialogTitleTypeface(): Typeface? {
        return this.mTitleTypeface
    }

    @JvmName("getDialogMessageTypeface")
    fun getDialogMessageTypeface(): Typeface? {
        return this.mMessageTypeface
    }

    @JvmName("getDialogPositiveTypeface")
    fun getDialogPositiveTypeface(): Typeface? {
        return this.mPositiveTypeface
    }

    @JvmName("getDialogNegativeTypeface")
    fun getDialogNegativeTypeface(): Typeface? {
        return this.mNegativeTypeface
    }

}