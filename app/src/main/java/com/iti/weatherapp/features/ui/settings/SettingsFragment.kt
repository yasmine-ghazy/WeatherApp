package com.iti.weatherapp.features.ui.settings

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iti.weatherapp.R
import com.iti.weatherapp.databinding.FragmentSettingsBinding
import com.iti.weatherapp.features.MainActivity
import com.iti.weatherapp.helper.Language
import com.iti.weatherapp.helper.SharedKey
import com.iti.weatherapp.helper.viewmodel.ViewModelFactory
import java.util.*


class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var binding: FragmentSettingsBinding

    val TAG = "SettingsFragment"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        settingsViewModel = ViewModelProvider(this, ViewModelFactory()).get(SettingsViewModel::class.java)
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        settingsViewModel.settings.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.i(TAG, "onCreateView: " + it)
            binding.languageTV.text = settingsViewModel.getTitle(SharedKey.LANGUAGE, requireContext())
            binding.locationTV.text = settingsViewModel.getTitle(SharedKey.LOCATION, requireContext())
            binding.windSpeedTV.text = settingsViewModel.getTitle(SharedKey.WIND, requireContext())
            binding.tempTV.text = settingsViewModel.getTitle(SharedKey.TEMP, requireContext())
            binding.alertTypeTV.text = settingsViewModel.getTitle(SharedKey.ALERT_TYPE, requireContext())
            //binding.alertTimeTV.text = settingsViewModel.settings.value?.get(SharedKey.ALERT_Time)
        })

        setupViews()
        settingsViewModel.getSettings()

        return binding.root
    }


    private fun setupViews(){

        binding.languageItem.setOnClickListener() {
            showMenu(it, R.menu.settings_language_menu, SharedKey.LANGUAGE){ item ->
//                val lang = if(item == 0) Locale.ENGLISH else Locale("ar")
                //ContextUtils(activity).updateLocale(requireContext(), lang)

                val lang = if(item == 0) "en" else "ar"
                Language.setLanguage(context = requireContext(), language = lang)
                (activity as MainActivity).recreate()
        } }
        binding.locationItem.setOnClickListener() {
            showMenu(it, R.menu.settings_location_menu, SharedKey.LOCATION){ item ->
        } }

        binding.alertTypeItem.setOnClickListener() {
            showMenu(it, R.menu.settings_alert_menu, SharedKey.ALERT_TYPE){ item ->
        } }
        binding.tempItem.setOnClickListener() {
            showMenu(it, R.menu.settings_temp_menu, SharedKey.TEMP){ item ->
        } }
        binding.windItem.setOnClickListener() {
            showMenu(it, R.menu.settings_wind_menu, SharedKey.WIND){ item ->
        } }
        binding.alertTimeItem.setOnClickListener() { showDatePicker() }
    }



    private fun showDatePicker() {
        val mcurrentTime: Calendar = Calendar.getInstance()
        val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute: Int = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(this.activity,
                { timePicker, selectedHour, selectedMinute ->
                    settingsViewModel.updateSettings(SharedKey.ALERT_Time, "$selectedHour:$selectedMinute")
                }, hour, minute, true) //Yes 24 hour time
        mTimePicker.setTitle(R.string.selectTime)
        mTimePicker.show()
    }


    private fun showMenu(view: View, menuId: Int, itemKey: SharedKey, itemSelected: (item: Int) -> (Unit)) = PopupMenu(view.context, view, Gravity.END).run {
        menuInflater.inflate(menuId, menu)
        menu.getItem(settingsViewModel.getSelectedIndex(itemKey)).isChecked = true
        //menu.findItem(itemIndex)?.isChecked = true
        setOnMenuItemClickListener { item ->
            settingsViewModel.updateSettings(itemKey, item.order)
            itemSelected(item.order)
            true
        }
        show()
    }

}