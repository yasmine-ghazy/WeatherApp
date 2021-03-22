package com.iti.weatherapp.helper.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.iti.weatherapp.helper.Language

open class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Language.setLanguage(requireContext(), Language.currentLanguage(requireContext()))

    }
}