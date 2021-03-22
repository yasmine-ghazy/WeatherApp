package com.iti.weatherapp.features.searchplace

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iti.weatherapp.R
import com.iti.weatherapp.model.City
import java.util.*

interface PlaceAdapterDelegate {
    fun itemClicked(city: City?)
}

class PlaceAdapter(private val delegate: PlaceAdapterDelegate) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder?>() {

    private var items: List<City> = ArrayList<City>()
    fun setItems(items: List<City>) {
        this.items = items
    }

    override fun onCreateViewHolder(recyclerview: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(recyclerview.getContext())
        val v: View = inflater.inflate(R.layout.row_place, recyclerview, false)
        val vh: ViewHolder = ViewHolder(v)
        Log.i(TAG, "==== onCreateViewHolder ====")
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.configure(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(
        layout
    ) {
        //MARK: - UIComponents
        var titleTextView: TextView
        var addressTextView: TextView
        var categoryTextView: TextView
        var linearLayout: LinearLayout
        fun configure(item: City) {
            titleTextView.setText(item.city)
            addressTextView.setText(item.region)
            categoryTextView.setText(item.country)
            linearLayout.setOnClickListener(View.OnClickListener { v: View? ->
                delegate.itemClicked(
                    item
                )
            })
        }

        init {
            titleTextView = layout.findViewById<TextView>(R.id.dayTextView)
            addressTextView = layout.findViewById<TextView>(R.id.addressTextView)
            categoryTextView = layout.findViewById<TextView>(R.id.categoryTextView)
            linearLayout = layout.findViewById<LinearLayout>(R.id.row)
        }
    }

    companion object {
        private const val TAG = "RecyclerView"
    }
}