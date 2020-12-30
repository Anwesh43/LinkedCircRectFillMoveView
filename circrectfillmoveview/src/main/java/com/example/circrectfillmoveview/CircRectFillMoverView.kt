package com.example.circrectfillmoveview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.app.Activity
import android.content.Context

val colors : Array<Int> = arrayOf(
    "#F44336",
    "#4CAF50",
    "#9C27B0",
    "#795548",
    "#2196F3"
).map {
    Color.parseColor(it)
}.toTypedArray()
val parts : Int = 4
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val rectWFactor : Float = 3.9f
val rectHFactor : Float = 8.9f
val circleFactor : Float = 6.9f
val delay : Long = 20
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawCircRectFillMover(scale : Float, w : Float, h : Float, paint : Paint) {
    val r : Float = Math.min(w, h) / circleFactor
    val rw : Float = Math.min(w, h) / rectWFactor
    val rh : Float = Math.min(w, h) / rectHFactor
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val sf3 : Float = sf.divideScale(2, parts)
    val sf4 : Float = sf.divideScale(3, parts)
    save()
    translate(w / 2, h / 2)
    save()
    translate(-w / 2 - rw * (1 - sf1), h / 4)
    paint.style = Paint.Style.STROKE
    drawRect(RectF(0f, 0f, rw, rh), paint)
    paint.style = Paint.Style.FILL
    drawRect(RectF(0f, 0f, rw * sf3, rh), paint)
    restore()
    save()
    translate(w / 2 - r * sf2, h / 4)
    paint.style = Paint.Style.STROKE
    drawCircle(0f, 0f, r, paint)
    paint.style = Paint.Style.FILL
    drawArc(RectF(-r, -r, r, r), 0f, 360f * sf4, true, paint)
    restore()
    restore()
}

fun Canvas.drawCRFMNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawCircRectFillMover(scale, w, h, paint)
}

class CircRectFillMoverView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN ->{

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            this.scale += scGap * dir
            if (Math.abs(this.scale - this.prevScale) > 1) {
                this.scale = this.prevScale + this.dir
                this.dir = 0f
                this.prevScale = this.scale
                cb(this.prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }
}