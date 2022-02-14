package com.thecodefather.whatsthepassword.ui.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.thecodefather.whatsthepassword.R
import com.thecodefather.whatsthepassword.WhatsPasswordApplication.Companion.context
import com.thecodefather.whatsthepassword.data.dtos.WelcomeScreenDto
import com.thecodefather.whatsthepassword.internal.extensions.debug
import com.thecodefather.whatsthepassword.internal.extensions.show

class OnboardingAdapter(private val listener: Listener) : PagerAdapter() {

    private var screens = listOf<WelcomeScreenDto>()

    private var ivIllustration: ImageView? = null
    private var tvTitle: TextView? = null
    private var tvDescription: TextView? = null
    private var shouldAnimateIllustration = true

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.item_onboarding, container, false) as ViewGroup
        val screen = screens[position]
        tvTitle = layout.findViewById(R.id.tvTitle)
        ivIllustration = layout.findViewById(R.id.ivIllustration)
        tvDescription = layout.findViewById(R.id.tvDescription)

        tvTitle?.text = screen.screenTitle
        tvDescription?.text = screen.screenText

        ivIllustration?.let {
            it.setImageResource(screen.imageId)
        }

        if (position == 0) {
            ivIllustration?.let {
                showRevealAnimationIfNeeded(it, tvTitle, tvDescription)
            }
        } else {
            ivIllustration?.show()
            tvTitle?.show()
            tvDescription?.show()
        }

        container.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun getCount(): Int = screens.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    private fun showRevealAnimationIfNeeded(
        illustration: ImageView,
        title: TextView?, description: TextView?
    ) {
        if (shouldAnimateIllustration) {
            startRevealAnimation(illustration) { //reveal is done, must animate illustration and circle
                animateText(title) { //title is done, must animate description
                    animateText(description) { //description is done, show indicator in home
                        listener.showIndicator()
                    }
                }
            }
            shouldAnimateIllustration = false
        } else {
            illustration?.show()
            title?.show()
            description?.show()
        }
    }

    private fun startRevealAnimation(myView: ImageView, callback: ((result: Unit) -> Unit)) {

        val animation = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                myView.show()
                callback.invoke(Unit)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
        myView.startAnimation(animation)
    }

    fun animateIllustration(view: ImageView?, callback: ((result: Unit) -> Unit)) {
        debug("animateIllustration, is view null? " + (view == null))
        view?.let {
            it.show()
            val animation = AnimationUtils
                .loadAnimation(context, R.anim.slide_from_right)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    callback.invoke(Unit)
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
            it.startAnimation(animation)
        }
    }

    fun animateText(view: TextView?, callback: ((result: Unit) -> Unit)) {
        debug("animateIllustration, is view null? " + (view == null))
        view?.let {
            it.show()
            val animation = AnimationUtils
                .loadAnimation(context, R.anim.slide_from_right)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    callback.invoke(Unit)
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
            view?.startAnimation(animation)
        }
    }

    fun update(screens: List<WelcomeScreenDto>) {
        this.screens = screens
        notifyDataSetChanged()
    }

    interface Listener{
        fun showIndicator()
    }
}