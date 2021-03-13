package com.iti.weatherapp.features.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.iti.weatherapp.databinding.FragmentHomeBinding
import com.iti.weatherapp.helper.UIHelper
import com.iti.weatherapp.helper.viewmodel.BaseFragment
import com.iti.weatherapp.helper.viewmodel.ViewModelFactory
import java.util.*


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
        setupRecycler()
        return binding.root
    }

    //endregion

    //region Methods
    private fun setupViewModel(){
        homeViewModel = ViewModelProvider(this, ViewModelFactory()).get(HomeViewModel::class.java)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            binding.textHome.text = it
        })

        homeViewModel.item.observe(viewLifecycleOwner, { weather ->

            binding.textCountry.text = "${weather.timezone}"
            binding.dailyRecycler.apply {
                weather.daily?.let { items ->
                    (adapter as DailyWeatherAdapter).updateItems(items = items)
                }
            }
            binding.hourlyRecycler.apply {
                weather.hourly?.let { items ->
                    (adapter as HourlyWeatherAdapter).updateItems(items.filter { item ->
                        item?.dt!! >= weather?.current?.dt!!
                    })
                }
            }

            weather.current?.let {
                binding.textDate.text = "${
                    UIHelper.getDateCurrentTimeZone(
                        it.dt ?: 0,
                        "EEEE MMM, dd"
                    )
                }"
                binding.textTime.text = "${UIHelper.getDateCurrentTimeZone(it.dt ?: 0, "hh:mm a")}"

                binding.textClouds.text = "☁️\n${it.clouds}"
                binding.textHeat.text = "☀\n${it.temp}℃"
                binding.textHumidity.text = "\uD83D\uDCA6\n${it.humidity}"

                binding.textWeather.text = "${it.weather?.get(0)?.description}"
                binding.textWindspeed.text = "\uD83C\uDF2C\n${it.windSpeed}"
                binding.textPressure.text = "⍖\n${it.pressure}"

            }

        })
        homeViewModel.getItems("33.441792", "-94.037689")
    }

    private fun setupRecycler() {
        binding.dailyRecycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = DailyWeatherAdapter()
        }

        binding.hourlyRecycler.apply {
            //layoutManager = LinearLayoutManager(activity)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = HourlyWeatherAdapter()
        }
    }

    //endregion
}