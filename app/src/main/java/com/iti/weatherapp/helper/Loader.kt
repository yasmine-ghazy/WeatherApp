package com.iti.weatherapp.helper

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.iti.weatherapp.R

class Loader(var context: Context) {
    var d: ProgressDialog
    fun start() {
        //if(NetworkClass.isNetworkConnected(context))
        d.show()
    }

    fun stop() {
        if (d.isShowing) d.dismiss()
    }

    init {
        d = ProgressDialog(context, R.style.MyGravity)
        d.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        d.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        d.setProgressDrawable(ColorDrawable(Color.WHITE))
        d.max = 1
    }
}