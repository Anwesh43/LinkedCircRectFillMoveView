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
val strokeFactor : Float = 90f
val rectWFactor : Float = 3.9f
val rectHFactor : Float = 8.9f
val circleFator : Float = 6.9f
val delay : Long = 20
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

