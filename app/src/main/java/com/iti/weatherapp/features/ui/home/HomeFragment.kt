package com.iti.weatherapp.features.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.iti.weatherapp.databinding.FragmentHomeBinding
import com.iti.weatherapp.helper.SharedKey
import com.iti.weatherapp.helper.SharedPreferenceHelper
import com.iti.weatherapp.helper.UIHelper
import com.iti.weatherapp.helper.viewmodel.ViewModelFactory
import com.iti.weatherapp.model.City


class HomeFragment(var city: City? = null) : DialogFragment() {

    //region properties
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    var PERMISSION_ID = 44
    var MAPS_ACTIVITY = 1
    var locationProvider: FusedLocationProviderClient? = null
    var location: Location? = null
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


        //init Properties
        locationProvider = LocationServices.getFusedLocationProviderClient(requireActivity())

//        currentLocation
//        when(SharedPreferenceHelper.readInt(SharedKey.LOCATION)){
//            0 -> currentLocation
//            1 -> {
//                val i = Intent(requireContext(), MapsActivity::class.java)
//                startActivityForResult(i, MAPS_ACTIVITY)
//            }
//        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        dialog?.apply {
            val params: ViewGroup.LayoutParams = this.window!!.attributes
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            this.window!!.attributes = params as WindowManager.LayoutParams
        }

        if (city == null){
            currentLocation
        }else{
            homeViewModel.getItems(city?.latitude, city?.longitude)
        }

    }

    //endregion

    //region Methods
    private fun setupViewModel() {
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

                binding.textHumidity.text = "\uD83D\uDCA6\n${it.humidity}"

                binding.textWeather.text = "${it.weather?.get(0)?.description}"

                binding.textPressure.text = "⍖\n${it.pressure}"

                binding.textHeat.text = "☀\n${it.temp}${UIHelper.getTempSympol()}"

                /**
                 * For temperature in Fahrenheit and wind speed in miles/hour, use units=imperial
                For temperature in Celsius and wind speed in meter/sec, use units=metric
                Temperature in Kelvin and wind speed in meter/sec is used by default, so there is no need to use the units parameter in the API call if you want this
                 * */

                val speed = SharedPreferenceHelper.readInt(SharedKey.WIND)
                val temp = (SharedPreferenceHelper.readInt(SharedKey.TEMP))

                var windSpeed = it.windSpeed ?: 0.0
                //Temp = Celsius - Speed = Mile/Sec
                if ((temp == 0 || temp == 1) && speed == 1) {
                    windSpeed = windSpeed.times(0.4)
                } else if (temp == 2 && speed == 0) {
                    windSpeed = windSpeed.times(2.2)
                }
                binding.textWindspeed.text = "\uD83C\uDF2C\n${windSpeed}"

            }

        })
//        homeViewModel.getItems("33.441792", "-94.037689")
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

    //region GPS
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                currentLocation
            }
        }
    }

    private fun checkPermissions(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private val isLocationEnabled: Boolean
        private get() {
            val locationManager = activity?.baseContext?.getSystemService(LOCATION_SERVICE) as LocationManager
            return if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            ) {
                true
            } else false
        }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.numUpdates = 1
        locationProvider!!.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            location = locationResult.lastLocation
            showLocation()
        }
    }

    @get:SuppressLint("MissingPermission")
    val currentLocation: Unit
        get() {
            if (checkPermissions()) {
                if (isLocationEnabled) {
                    requestNewLocationData()
                } else {
                    enableLocation()
                }
            } else {
                requestPermissions()
            }
        }

    private fun showLocation() {
        Log.i(TAG, "showLocation: $location")
        homeViewModel.getItems(location?.latitude, location?.longitude)
    }

    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(message)
            .setCancelable(true)
            .setPositiveButton("OK", null)
        val alert = builder.create()
        alert.show()
    }

    private fun enableLocation() {
        Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
        val i = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(i)
    }
}