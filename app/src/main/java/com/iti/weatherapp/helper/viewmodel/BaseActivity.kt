package com.iti.weatherapp.helper.viewmodel

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.iti.weatherapp.helper.Language


open class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        Language.setLanguage(this, Language.currentLanguage(this))
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //Language.setLanguage(this, Language.currentLanguage(this))
        //recreate()
    }
}