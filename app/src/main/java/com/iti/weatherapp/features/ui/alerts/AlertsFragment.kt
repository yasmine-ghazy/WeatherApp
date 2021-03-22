package com.iti.weatherapp.features.ui.alerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.iti.weatherapp.databinding.FragmentAlertsBinding
import com.iti.weatherapp.helper.viewmodel.BaseFragment
import com.iti.weatherapp.helper.viewmodel.ViewModelFactory

class AlertsFragment : BaseFragment() {

    //region properties
    private lateinit var viewModel: AlertsViewModel
    private lateinit var binding: FragmentAlertsBinding
    val TAG = "AlertsFragment"

    //endregion

    //region FragmentLifeCycle
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAlertsBinding.inflate(inflater, container, false)
        setupViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    //endregion

    //region Methods
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory()).get(AlertsViewModel::class.java)

        viewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textAlerts.text = it
        })
    }
}


