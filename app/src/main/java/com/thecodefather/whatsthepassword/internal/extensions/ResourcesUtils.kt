package com.thecodefather.whatsthepassword.internal.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.thecodefather.whatsthepassword.WhatsPasswordApplication as application


/**
 * Created by Hussein pm 8/12/2018.
 */

fun getStringResource(@StringRes id: Int, context: Context = application.context): String {
    return context.getString(id)
}

fun getStringResource(@StringRes id: Int, vararg args: Any): String {
    val context = application.context
    return context.getString(id, *args)
}

fun getColorResource(@ColorRes id: Int): Int {
    return ContextCompat.getColor(application.context, id)
}

fun getDrawable(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(application.context, id)
}

fun getAnimation(id: Int?): Animation? {
    return if (id == -1 || id == null) {
        null
    } else {
        AnimationUtils.loadAnimation(application.context, id)
    }

}

fun getDimen(@DimenRes id: Int): Float {
    val context = application.context
    return context.resources.getDimension(id)
}
