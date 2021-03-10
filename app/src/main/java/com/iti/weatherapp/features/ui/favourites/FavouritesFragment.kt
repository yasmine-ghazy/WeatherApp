package com.iti.weatherapp.features.ui.favourites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.iti.weatherapp.R
import com.iti.weatherapp.databinding.FragmentFavouritesBinding
import com.iti.weatherapp.databinding.FragmentHomeBinding
import com.iti.weatherapp.features.ui.home.HomeViewModel
import com.iti.weatherapp.helper.viewmodel.BaseFragment
import com.iti.weatherapp.helper.viewmodel.ViewModelFactory
import com.iti.weatherapp.repo.WeatherRepo

class FavouritesFragment : BaseFragment() {

    //region properties
    private lateinit var viewModel: FavouritesViewModel
    private lateinit var binding: FragmentFavouritesBinding
    val TAG = "HomeFragment"

    //endregion

    //region FragmentLifeCycle
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        setupViewModel()
        initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    //endregion

    //region Methods
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory()).get(FavouritesViewModel::class.java)

        viewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textFavourites.text = it
        })
    }

    private fun initViews() {
        binding.fab.setOnClickListener() {
            Snackbar.make(binding.root, "Add", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()}
        }

        //endregion
    }