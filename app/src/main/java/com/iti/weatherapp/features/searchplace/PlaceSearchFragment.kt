package com.iti.weatherapp.features.searchplace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iti.weatherapp.R
import com.iti.weatherapp.model.model.Place

/**
 * A simple [Fragment] subclass.
 * Use the [PlaceSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlaceSearchFragment : Fragment(), PlaceAdapterDelegate {
    //MARK: - UIComponents
    private var recyclerView: RecyclerView? = null
    private var searchView: SearchView? = null

    //MARK - Properties
    private var adapter: PlaceAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var viewModel: PlaceViewModel? = null
    private var searchText: String? = null
    private var delegate: PlaceAdapterDelegate? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            searchText = arguments?.getString(SEARCH_TEXT)
        }
        Log.i(TAG, "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: ")
        val view: View = inflater.inflate(R.layout.fragment_place_search, container, false)
        initViews(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ")
        setupRecycler()
        configureViews()
        setupViewModel()
        setupSearchView()
    }

    fun initViews(view: View) {
        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recycler_view)
    }

    fun setupSearchView() {
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                Log.i(TAG, "onQueryTextSubmit$s")
                viewModel?.onQueryTextSubmit(s)
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                Log.i(TAG, "onQueryTextChange$text")
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
        viewModel?.itemsList?.observe(viewLifecycleOwner) { items -> updateItems(items) }
    }

    //MARK: - Methods
    private fun setupRecycler() {
        recyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        recyclerView?.setLayoutManager(layoutManager)
    }

    private fun configureViews() {
        adapter = PlaceAdapter(context, this)
        recyclerView?.setAdapter(adapter)
    }

    //MARK: - Implement ViewDelegate Methods
    override fun itemClicked(place: Place?) {
        delegate!!.itemClicked(place)
        close()
    }

    fun updateItems(items: List<Place>) {
        adapter!!.setItems(items)
        adapter?.notifyDataSetChanged()
    }

    fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        Log.i(TAG, "onDestroyView")
        super.onDestroyView()
        viewModel?.dispose()
    }

    fun close() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this@PlaceSearchFragment)
            ?.commit()
    }

    companion object {
        const val TAG = "PlaceSearchFragment"

        //MARK: - Properties
        private const val SEARCH_TEXT = "search"

        // TODO: Rename and change types and number of parameters
        fun newInstance(searchText: String?, delegate: PlaceAdapterDelegate?): PlaceSearchFragment {
            val fragment = PlaceSearchFragment()
            val args = Bundle()
            args.putString(SEARCH_TEXT, searchText)
            fragment.arguments = args
            fragment.delegate = delegate
            Log.i(TAG, "newInstance: ")
            return fragment
        }
    }
}