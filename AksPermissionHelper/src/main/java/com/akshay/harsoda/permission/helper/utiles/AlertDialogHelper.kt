package com.akshay.harsoda.permission.helper.utiles

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.text.style.MetricAffectingSpan
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog

/**
 * @author Akshay Harsoda
 * @since 18 Mar 2021
 */
@JvmOverloads
@JvmName("showAlert")
fun Context.showAlert(
    @Nullable fTitle: String? = null,
    @Nullable fMessage: String? = null,
    @Nullable fPositiveText: String? = null,
    @Nullable fNegativeText: String? = null,
    @Nullable fNeutralText: String? = null,
    @ColorInt fTitleColor: Int? = null,
    @ColorInt fMessageColor: Int? = null,
    @ColorInt fPositiveColor: Int? = null,
    @ColorInt fNegativeColor: Int? = null,
    @ColorInt fNeutralColor: Int? = null,
    @ColorInt fButtonColor: Int? = null,
    @ColorInt fAllTextColor: Int? = null,
    @Nullable fTitleTypeface: Typeface? = null,
    @Nullable fMessageTypeface: Typeface? = null,
    @Nullable fPositiveTypeface: Typeface? = null,
    @Nullable fNegativeTypeface: Typeface? = null,
    @Nullable fNeutralTypeface: Typeface? = null,
    @Nullable fButtonTypeface: Typeface? = null,
    @Nullable fAllTextTypeface: Typeface? = null,
    @NonNull fButtonClickListener: OnAlertButtonClickListener
) {
    val lBuilder = AlertDialog.Builder(this)
    var dialog: AlertDialog? = null

    lBuilder.setCancelable(false)

    fTitle?.let { title ->
        var ssBuilder = SpannableStringBuilder(title)

        fTitleColor?.let { color ->
            ssBuilder = getColorSpannableString(ssBuilder, color)
        }

        fAllTextColor?.let { color ->
            ssBuilder = getColorSpannableString(ssBuilder, color)
        }

        fTitleTypeface?.let { typeface ->
            ssBuilder = getTypefaceSpannableString(ssBuilder, typeface)
        }

        fAllTextTypeface?.let { typeface ->
            ssBuilder = getTypefaceSpannableString(ssBuilder, typeface)
        }

        lBuilder.setTitle(ssBuilder)
    }

    fMessage?.let { text ->

        var ssBuilder = SpannableStringBuilder(text)

        fMessageColor?.let { color ->
            ssBuilder = getColorSpannableString(ssBuilder, color)
        }

        fAllTextColor?.let { color ->
            ssBuilder = getColorSpannableString(ssBuilder, color)
        }

        fMessageTypeface?.let { typeface ->
            ssBuilder = getTypefaceSpannableString(ssBuilder, typeface)
        }

        fAllTextTypeface?.let { typeface ->
            ssBuilder = getTypefaceSpannableString(ssBuilder, typeface)
        }

        lBuilder.setMessage(ssBuilder)
    }

    fPositiveText?.let { text ->

        var ssBuilder = SpannableStringBuilder(text)

        fPositiveColor?.let { color ->
            ssBuilder = getColorSpannableString(ssBuilder, color)
        }

        fButtonColor?.let { color ->
            ssBuilder = getColorSpannableString(ssBuilder, color)
        }

        fPositiveTypeface?.let { typeface ->
            ssBuilder = getTypefaceSpannableString(ssBuilder, typeface)
        }

        fButtonTypeface?.let { typeface ->
            ssBuilder = getTypefaceSpannableString(ssBuilder, typeface)
        }

        fAllTextTypeface?.let { typeface ->
            ssBuilder = getTypefaceSpannableString(ssBuilder, typeface)
        }

        lBuilder.setPositiveButton(ssBuilder) { _, _ ->
            dialog?.dismiss()
            fButtonClickListener.onPositiveButtonClick()
        }
    }

    fNegativeText?.let { text ->

        var ssBuilder = SpannableStringBuilder(text)

        fNegativeColor?.let { color ->
            ssBuilder = getColorSpannableString(ssBuilder, color)
        }

        fButtonColor?.let { color ->
            ssBuilder = getColorSpannableString(ssBuilder, color)
        }

        fNegativeTypeface?.let { typeface ->
            ssBuilder = getTypefaceSpannableString(ssBuilder, typeface)
        }

        fButtonTypeface?.let { typeface ->
            ssBuilder = getTypefaceSpannableString(ssBuilder, typeface)
        }

        fAllTextTypeface?.let { typeface ->
            ssBuilder = getTypefaceSpannableString(ssBuilder, typeface)
        }

        lBuilder.setNegativeButton(ssBuilder) { _, _ ->
            dialog?.dismiss()
            fButtonClickListener.onNegativeButtonClick()
        }
    }

    fNeutralText?.let { text ->

        var ssBuilder = SpannableStringBuilder(text)

        fNeutralColor?.let { color ->
            ssBuilder = getColorSpannableString(ssBuilder, color)
        }

        fButtonColor?.let { color ->
            ssBuilder = getColorSpannableString(ssBuilder, color)
        }

        fNeutralTypeface?.let { typeface ->
            ssBuilder = getTypefaceSpannableString(ssBuilder, typeface)
        }

        fButtonTypeface?.let { typeface ->
            ssBuilder = getTypefaceSpannableString(ssBuilder, typeface)
        }

        fAllTextTypeface?.let { typeface ->
            ssBuilder = getTypefaceSpannableString(ssBuilder, typeface)
        }

        lBuilder.setNeutralButton(ssBuilder) { _, _ ->
            dialog?.dismiss()
            fButtonClickListener.onNeutralButtonClick()
        }
    }

    dialog = lBuilder.create()
    dialog.show()
}

private fun getColorSpannableString(
    fSource: SpannableStringBuilder,
    @ColorInt fColor: Int
): SpannableStringBuilder {
    val foregroundColorSpan = ForegroundColorSpan(fColor)
    fSource.setSpan(
        foregroundColorSpan,
        0,
        fSource.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return fSource
}

private fun getTypefaceSpannableString(
    fSource: SpannableStringBuilder,
    fTypeface: Typeface
): SpannableStringBuilder {
    val typefaceSpan = TypefaceSpan(fTypeface)
    fSource.setSpan(
        typefaceSpan,
        0,
        fSource.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return fSource
}

private class TypefaceSpan(private val typeface: Typeface) : MetricAffectingSpan() {

    override fun updateDrawState(tp: TextPaint) {
        tp.typeface = typeface
        tp.flags = tp.flags or Paint.SUBPIXEL_TEXT_FLAG
    }

    override fun updateMeasureState(p: TextPaint) {
        p.typeface = typeface
        p.flags = p.flags or Paint.SUBPIXEL_TEXT_FLAG
    }
}

interface OnAlertButtonClickListener {
    fun onPositiveButtonClick()
    fun onNegativeButtonClick() {}
    fun onNeutralButtonClick() {}
}