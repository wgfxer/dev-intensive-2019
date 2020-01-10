package ru.skillbranch.devintensive.ui.custom

import android.graphics.*
import android.graphics.drawable.Drawable

class InitialsDrawable(val initials: String, circleColor: Int) : Drawable() {

    val circlePaint: Paint = Paint()
    val initialsPaint: Paint = Paint()
    val circleRect: RectF = RectF(0.0f, 0.0f, 220.0f, 220.0f)

    init {
        circlePaint.isAntiAlias = true
        initialsPaint.isAntiAlias = true
        initialsPaint.color = Color.WHITE
        circlePaint.color = circleColor
        initialsPaint.textSize = 46f
    }


    override fun draw(canvas: Canvas) {
        canvas.drawOval(circleRect, circlePaint)
        canvas.drawText(initials, 110f, 110f, initialsPaint)
    }

    override fun setAlpha(alpha: Int) {
        circlePaint.alpha = alpha
        initialsPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        circlePaint.colorFilter = colorFilter
        initialsPaint.colorFilter = colorFilter
    }
}