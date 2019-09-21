package com.riluq.dicodingacademyjetpack.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity.CENTER
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.riluq.dicodingacademyjetpack.R


class MyButton: AppCompatButton {

    private var enabledBackground: Drawable? = null
    private var disabledBackground: Drawable? = null
    private var textColor: Int? = null

    constructor(context: Context): super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = if (isEnabled) enabledBackground else disabledBackground
        setTextColor(textColor!!)
        textSize = 12f
        gravity = CENTER

    }

    private fun init() {
        val resource = resources
        enabledBackground = resource.getDrawable(R.drawable.bg_button)
        disabledBackground = resource.getDrawable(R.drawable.bg_button_disable)
        textColor = ContextCompat.getColor(context, android.R.color.background_light)
    }
}