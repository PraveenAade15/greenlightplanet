package com.example.greenlight.ui

import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenlight.R
import com.example.greenlight.callbacks.CountryClickListener
import com.example.greenlight.database.DataStore
import com.example.greenlight.databinding.FragmentZoneBinding
import com.example.greenlight.models.AreaResponse
import com.example.greenlight.models.ResponseData
import com.example.greenlight.models.SalesArea
import com.example.greenlight.models.SalesCountry
import com.example.greenlight.ui.adapter.AreaAdapter
import com.example.greenlight.utils.NetworkResult
import com.example.greenlight.viewmodel.AreaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ZoneFragment : Fragment(), CountryClickListener {
    private var _binding: FragmentZoneBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataStore: DataStore
    private lateinit var response: ResponseData
    private val viewModel by viewModels<AreaViewModel>()
    private lateinit var areaAdapter: AreaAdapter
    private var str: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentZoneBinding.inflate(layoutInflater, container, false)
        viewModel.getAllArea()
        binding.recyclerViewArea.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewArea.isNestedScrollingEnabled = false
        initbserve()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.back.setOnClickListener {

            if (str.equals("sales_country")) {
                //back close
                val isSuccess = findNavController().navigateUp()
                if (!isSuccess) requireActivity().onBackPressed()
            } else if (str.equals("sales_zone")) {
                areaAdapter.upDateList(dataStore.sales_country)
            } else if (str.equals("sales_region")) {
                areaAdapter.upDateList(dataStore.sales_zone)
            } else if (str.equals("sales_area")) {
                areaAdapter.upDateList(dataStore.sales_region)
            }
        }
    }

    private fun initbserve() {
        viewModel.areaLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    Log.d(ContentValues.TAG, "bindObserversjhdb: " + it.data.toString())
                    response = it.data!!.ResponseData
                    val countryResponse = ArrayList<com.example.greenlight.database.AreaResponse>()
                    val zoneResponse = ArrayList<com.example.greenlight.database.AreaResponse>()
                    val saleRegionResponse =
                        ArrayList<com.example.greenlight.database.AreaResponse>()
                    val saleAreaResponse = ArrayList<com.example.greenlight.database.AreaResponse>()
//                    binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//
//                        override fun onQueryTextChange(newText: String): Boolean {
//                            adapter.filter.filter(newText)
//                            adapter.notifyDataSetChanged()
//                            return true
//                        }
//
//                        override fun onQueryTextSubmit(query: String): Boolean {
//                            return false
//                        }
//
//                    })
                    response.sales_country.forEach {
                        countryResponse.add(
                            com.example.greenlight.database.AreaResponse(
                                "sales_country",
                                it.country
                            )
                        )
                    }
                    response.sales_zone.forEach {
                        zoneResponse.add(
                            com.example.greenlight.database.AreaResponse(
                                "sales_zone",
                                it.zone
                            )
                        )
                    }
                    response.sales_region.forEach {
                        saleRegionResponse.add(
                            com.example.greenlight.database.AreaResponse(
                                "sales_region",
                                it.region
                            )
                        )
                    }
                    response.sales_area.forEach {

                        saleAreaResponse.add(
                            com.example.greenlight.database.AreaResponse(
                                "sales_area",
                                it.area
                            )
                        )
                    }

                    dataStore = DataStore(
                        saleAreaResponse,
                        countryResponse,
                        saleRegionResponse,
                        zoneResponse
                    )
                    // list = it.data?.ResponseData as List<SalesArea>
                    binding.apply {
//                        recyclerviewMovie.setHasFixedSize(true)
                        str = countryResponse.firstOrNull()?.name
                        areaAdapter = AreaAdapter(countryResponse, this@ZoneFragment)
                        recyclerViewArea.adapter = areaAdapter
                        areaAdapter.notifyDataSetChanged()


                    }
                    Log.d(ContentValues.TAG, "bindObserversjhdb: " + it.data.toString())
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {

                }
            }

        })
    }
    private fun performSearch() {
        binding.search .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                search(newText)
                return true
            }
        })
    }


    override fun clickOnArea(
        areaResponse: com.example.greenlight.database.AreaResponse
    ) {
        str = areaResponse?.name

        if (areaResponse.name.equals("sales_country")) {
            binding.textPer.text = areaResponse.value + "  Performance"
            areaAdapter.upDateList(dataStore.sales_zone)
        } else if (areaResponse.name.equals("sales_zone")) {
            binding.textPer.text = areaResponse.value + "  Performance"
            areaAdapter.upDateList(dataStore.sales_region)
        } else if (areaResponse.name.equals("sales_region")) {
            binding.textPer.text = areaResponse.value + "  Performance"
            areaAdapter.upDateList(dataStore.sales_area)
        } else {
            binding.search.visibility=View.VISIBLE
            binding.search.setQueryHint("Search by Name");
            binding.textPer.text = areaResponse.value + "  Performance"

        }

//        Toast.makeText(context,"Cell clicked", Toast.LENGTH_SHORT).show()
    }


}