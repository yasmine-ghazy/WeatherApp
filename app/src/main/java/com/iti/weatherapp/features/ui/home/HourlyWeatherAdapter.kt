package com.iti.weatherapp.features.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iti.weatherapp.databinding.HourlyWeatherRowLayoutBinding
import com.iti.weatherapp.helper.UIHelper
import com.iti.weatherapp.model.DailyItem
import com.iti.weatherapp.model.HourlyItem


class HourlyWeatherAdapter(private var items: List<HourlyItem?> = listOf()) : RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder>() {

    lateinit var viewBinding: HourlyWeatherRowLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        viewBinding = HourlyWeatherRowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        items.get(position)?.let {item ->
            holder.myView.dayTextView.text = item.dt?.let { UIHelper.getDateCurrentTimeZone(it, "hh:mm a") }
            holder.myView.descTextView.text = item.weather?.get(0)?.description
            holder.myView.tempTextView.text = item.temp?.let {temp -> "${temp}℃"  }
            item.weather?.get(0)?.icon?.let { icon ->
                val iconUrl = "http://openweathermap.org/img/w/$icon.png"
                Glide.with(viewBinding.root).load(iconUrl).apply(RequestOptions()).into(holder.myView.imageView)
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<HourlyItem?>){
        this.items = items
        this.notifyDataSetChanged()
    }

    class ViewHolder(var myView: HourlyWeatherRowLayoutBinding) : RecyclerView.ViewHolder(myView.root)
}
