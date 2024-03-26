package com.example.tw_movie_app.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View


fun Context.pxToDp(px: Int): Int = (px / Resources.getSystem().displayMetrics.density).toInt()

fun View.pxToDp(px: Int): Int = context.pxToDp(px)

fun View.dpToPx(dp: Int): Int = context.dpToPx(dp)

fun Context.dpToPx(dp: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()