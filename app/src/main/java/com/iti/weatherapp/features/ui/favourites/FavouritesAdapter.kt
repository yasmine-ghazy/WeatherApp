package com.iti.weatherapp.features.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iti.weatherapp.databinding.RowDailyWeatherBinding
import com.iti.weatherapp.databinding.RowPlaceBinding
import com.iti.weatherapp.features.searchplace.PlaceAdapterDelegate
import com.iti.weatherapp.helper.UIHelper
import com.iti.weatherapp.model.City
import com.iti.weatherapp.model.DailyItem


class FavouritesAdapter(private val delegate: PlaceAdapterDelegate) : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    lateinit var viewBinding: RowPlaceBinding
    private var items: List<City?> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        viewBinding = RowPlaceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items.get(position)?.let {item ->
            holder.myView.dayTextView.setText(item.city)
            holder.myView.addressTextView.setText(item.region)
            holder.myView.categoryTextView.setText(item.country)
            holder.myView.row.setOnClickListener {
                delegate.itemClicked(city = item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<City?>){
        this.items = items
        this.notifyDataSetChanged()
    }

    class ViewHolder(var myView: RowPlaceBinding) : RecyclerView.ViewHolder(myView.root)
}
