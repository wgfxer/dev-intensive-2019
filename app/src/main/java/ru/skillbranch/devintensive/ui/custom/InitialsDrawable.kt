package ru.skillbranch.devintensive.ui.custom

import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.graphics.toRectF

class InitialsDrawable(val initials: String, circleColor: Int) : Drawable() {

    val circlePaint: Paint = Paint()
    val initialsPaint: Paint = Paint()

    init {
        circlePaint.isAntiAlias = true
        initialsPaint.isAntiAlias = true
        initialsPaint.color = Color.WHITE
        circlePaint.color = circleColor
        initialsPaint.textAlign = Paint.Align.CENTER
    }


    override fun draw(canvas: Canvas) {
        canvas.drawOval(bounds.toRectF(), circlePaint)
        val offsetY = (initialsPaint.descent() + initialsPaint.ascent()) / 2
        initialsPaint.textSize = bounds.height() * 0.33f
        canvas.drawText(
            initials,
            bounds.exactCenterX(),
            bounds.exactCenterY() - offsetY,
            initialsPaint
        )
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