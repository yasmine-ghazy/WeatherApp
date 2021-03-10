package com.iti.weatherapp.features.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iti.weatherapp.R
import com.iti.weatherapp.databinding.FragmentHomeBinding
import com.iti.weatherapp.helper.viewmodel.BaseFragment
import com.iti.weatherapp.helper.viewmodel.ViewModelFactory


class HomeFragment : BaseFragment(){

    //region properties
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    val TAG = "HomeFragment"

    //endregion

    //region FragmentLifeCycle
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupViewModel()
        return binding.root
    }

    //endregion

    //region Methods
    private fun setupViewModel(){
        homeViewModel = ViewModelProvider(this, ViewModelFactory()).get(HomeViewModel::class.java)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textHome.text = it
        })

        homeViewModel.item.observe(viewLifecycleOwner, {
            Log.i(TAG, "setupViewModel: ${it.lat}")
        })
        homeViewModel.getItems("33.441792", "-94.037689")
    }

    //endregion
}