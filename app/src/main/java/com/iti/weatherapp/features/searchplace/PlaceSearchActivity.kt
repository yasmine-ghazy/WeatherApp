package com.iti.weatherapp.features.searchplace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iti.weatherapp.R
import com.iti.weatherapp.helper.viewmodel.BaseActivity
import com.iti.weatherapp.model.City


class PlaceSearchActivity : BaseActivity(), PlaceAdapterDelegate {
    //MARK: - UIComponents
    private var recyclerView: RecyclerView? = null
    private var searchView: SearchView? = null

    //MARK - Properties
    private var adapter: PlaceAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var viewModel: PlaceViewModel? = null
    private var searchText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_search)
        initViews()
        setupRecycler()
        configureViews()
        setupViewModel()
        setupSearchView()
    }

    fun initViews() {
        searchView = findViewById(R.id.searchView)
        recyclerView = findViewById(R.id.recycler_view)
    }

    fun setupSearchView() {
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                viewModel?.onQueryTextSubmit(s)
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                viewModel?.onQueryTextChange(text)
                return true
            }
        })
        searchView!!.setOnCloseListener {
            close()
            true
        }

        //Set SearchView Focus
        searchView!!.isFocusable = true
        searchView!!.isIconified = false
        searchView!!.requestFocusFromTouch()
    }

    fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        viewModel?.itemsList?.observe(this) { items -> updateItems(items) }
    }

    //MARK: - Methods
    private fun setupRecycler() {
        recyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)
    }

    private fun configureViews() {
        adapter = PlaceAdapter(this)
        recyclerView?.setAdapter(adapter)
    }

    //MARK: - Implement ViewDelegate Methods
    override fun itemClicked(city: City?) {
        val returnIntent = Intent()
        returnIntent.putExtra("city", city)
        setResult(RESULT_OK, returnIntent)
        close()
    }

    fun updateItems(items: List<City>) {
        adapter!!.setItems(items)
        adapter?.notifyDataSetChanged()
    }

    fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun close() {
        finish()
    }
}