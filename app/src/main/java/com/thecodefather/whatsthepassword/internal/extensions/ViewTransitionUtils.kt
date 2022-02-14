package com.thecodefather.whatsthepassword.internal.extensions

import android.animation.Animator
import android.view.View


fun View.hideView() {
    this.apply {
        alpha = 1f
        animate()
            .alpha(0f)
            .translationYBy(this.height.toFloat())
            .setDuration(200)
            .setListener(object: Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    visibility = View.GONE
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }
            })
    }
}

fun View.showView() {
    this.apply {
        alpha = 0f
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .translationYBy(-this.height.toFloat())
            .setDuration(200)
            .setListener(null)
    }
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}