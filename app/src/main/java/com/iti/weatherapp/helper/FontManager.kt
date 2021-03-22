package com.iti.weatherapp.helper

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


object FontManager {
    const val ROOT = "fonts/"
    const val FONTAWESOME = ROOT + "fontawesome-webfont.ttf"
    fun getTypeface(context: Context, font: String?): Typeface {
        return Typeface.createFromAsset(context.getAssets(), font)
    }

    fun markAsIconContainer(v: View, typeface: Typeface?) {
        if (v is ViewGroup) {
            val vg = v as ViewGroup
            for (i in 0 until vg.childCount) {
                val child: View = vg.getChildAt(i)
                markAsIconContainer(v, null)
            }
        } else if (v is TextView) {
            (v as TextView).setTypeface(typeface)
        }
    }
}