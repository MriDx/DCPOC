package com.mridx.androidtemplate.presentation.utils

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.mridx.androidtemplate.utils.parseException

@set:BindingAdapter("visible")
var View.visible
    get() = this.isVisible
    set(value) {
        this.isVisible = value
    }

@set:BindingAdapter("imageUrl")
var AppCompatImageView.imageUrl
    get() = ""
    set(value) {
        Glide.with(this)
            .asBitmap()
            .load(value)
            .into(this)
    }


@set:BindingAdapter("drawableResource")
var AppCompatImageView.drawableResource
    get() = 0
    set(value) {
        setImageResource(value)
    }


@set:BindingAdapter("buttonIcon")
var MaterialButton.buttonIcon
    get() = 0
    set(value) {
        icon = ContextCompat.getDrawable(context, value)
    }


@BindingAdapter("android:valueAttrChanged")
fun setSliderListeners(slider: Slider, attrChange: InverseBindingListener) {
    slider.addOnChangeListener { _, _, _ ->
        attrChange.onChange()
    }
}

@set:BindingAdapter("pipeLength")
var MaterialTextView.pipeLength
    get() = 0f
    set(value) {
        text = "${value.toInt()} meter"
    }

@set:BindingAdapter("textResource")
var MaterialTextView.textResource
    get() = 0
    set(value) {
        text = resources.getString(value)
    }


@BindingAdapter("errorState")
fun TextView.errorState(state: LoadState) {
    val exception = (state as? LoadState.Error)?.error ?: return
    isVisible = !exception.message.isNullOrEmpty()
    text = parseException(e = exception)
    //text = exception?.message
}

@BindingAdapter("removeError")
fun TextInputLayout.removeError(removeError: Boolean = true) {
    editText?.doBeforeTextChanged { text, start, count, after ->
        this.error = null
    }
}