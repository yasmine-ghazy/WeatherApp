package com.iti.weatherapp.features.ui.favourites

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iti.weatherapp.R
import com.iti.weatherapp.databinding.FragmentFavouritesBinding
import com.iti.weatherapp.features.searchplace.PlaceAdapterDelegate
import com.iti.weatherapp.features.searchplace.PlaceSearchActivity
import com.iti.weatherapp.features.ui.home.HomeFragment
import com.iti.weatherapp.helper.MyApplication
import com.iti.weatherapp.helper.viewmodel.BaseFragment
import com.iti.weatherapp.model.City

class FavouritesFragment : BaseFragment() {

    //region properties
    //private lateinit var viewModel: FavouritesViewModel
    private lateinit var binding: FragmentFavouritesBinding
    val TAG = "HomeFragment"
    val LAUNCH_SECOND_ACTIVITY = 1
    private val viewModel: FavouritesViewModel by viewModels {
        FavouritesViewModelFactory((activity?.application as MyApplication).repository)
    }
    //endregion

    //region FragmentLifeCycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        initViews()
        setupViewModel()
        setupRecycler()
        setHasOptionsMenu(true)
        return binding.root
    }


    // create an action bar button
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
        super.onCreateOptionsMenu(menu!!, inflater)
    }


    // handle button activities
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == R.id.add_button) {
            // do something here
            startActivityForResult(
                Intent(requireContext(), PlaceSearchActivity::class.java),
                LAUNCH_SECOND_ACTIVITY
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    it.getParcelableExtra<City>("city")?.let {
                        viewModel.addItem(city = it)
                    }
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
    //endregion

    //region Methods
    private fun initViews() {
    }

    private fun setupViewModel(){
        //viewModel = ViewModelProvider(this, ViewModelFactory()).get(FavouritesViewModel::class.java)
        viewModel.items.observe(viewLifecycleOwner, { items ->
            binding.recyclerView.apply {
                (adapter as FavouritesAdapter).updateItems(items = items)
                binding.noDataTV.visibility = if (items.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
        })
    }

    private fun setupRecycler() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = FavouritesAdapter(object : PlaceAdapterDelegate {
                override fun itemClicked(city: City?) {
                    HomeFragment(city = city).show(
                        childFragmentManager, TAG
                    )
                }
            })

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    v: RecyclerView,
                    h: RecyclerView.ViewHolder,
                    t: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) {
                            viewModel.removeItem(viewModel.items.value?.get(h.adapterPosition)?.id ?: 0)
                            (adapter as FavouritesAdapter).notifyItemRemoved(h.adapterPosition)
                }
            }).attachToRecyclerView(binding.recyclerView)
        }

    }
    //endregion
}