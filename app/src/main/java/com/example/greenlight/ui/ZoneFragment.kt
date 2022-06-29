package com.example.greenlight.ui

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenlight.callbacks.CountryClickListener
import com.example.greenlight.database.AreaResponse
import com.example.greenlight.database.DataStore
import com.example.greenlight.databinding.FragmentZoneBinding
import com.example.greenlight.models.ResponseData
import com.example.greenlight.models.SalesArea
import com.example.greenlight.ui.adapter.AreaAdapter
import com.example.greenlight.utils.NetworkResult
import com.example.greenlight.viewmodel.AreaViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ZoneFragment : Fragment(), CountryClickListener {
    private var _binding: FragmentZoneBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataStore: DataStore
    private lateinit var response: ResponseData
    private val viewModel by viewModels<AreaViewModel>()
    private lateinit var areaAdapter: AreaAdapter
    private var str: String? = null
    var objectListNew = ArrayList<AreaResponse>()
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
        initObserve()
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (str.equals("sales_area")) {
                    binding.search.visibility = View.GONE
                    Log.d("TAG", "onViewCreatedb: ")
                    dataStore.sales_region?.let { it1 -> areaAdapter.upDateList(it1) }

                } else if (str.equals("sales_region")) {
                    binding.search.visibility = View.GONE
                    dataStore.sales_zone?.let { it1 -> areaAdapter.upDateList(it1) }
                } else if (str.equals("sales_zone")) {
                    binding.search.visibility = View.GONE
                    dataStore.sales_country?.let { it1 -> areaAdapter.upDateList(it1) }

                } else if (str.equals("sales_country")) {
                    //back close
                    val isSuccess = findNavController().navigateUp()
                    if (!isSuccess) requireActivity().onBackPressed()

                }
            }

        })
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickFunction()

        binding.back.setOnClickListener {
            Log.d("TAG", "onViewCreatedb: " + str)
            if (str.equals("sales_area")) {
                binding.search.visibility = View.GONE
                Log.d("TAG", "onViewCreatedb: ")
                dataStore.sales_region?.let { it1 -> areaAdapter.upDateList(it1) }

            } else if (str.equals("sales_region")) {
                binding.search.visibility = View.GONE
                dataStore.sales_zone?.let { it1 -> areaAdapter.upDateList(it1) }
            } else if (str.equals("sales_zone")) {
                binding.search.visibility = View.GONE
                dataStore.sales_country?.let { it1 -> areaAdapter.upDateList(it1) }

            } else if (str.equals("sales_country")) {
                //back close
                val isSuccess = findNavController().navigateUp()
                if (!isSuccess) requireActivity().onBackPressed()

            }
        }
    }

    private fun initObserve() {
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
                    binding.apply {
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

    private fun clickFunction() {
        binding.search.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int

            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                objectListNew.clear()
                Log.d("TAG", "::::str " + charSequence.toString())
                for (j in dataStore.sales_area!!.indices) {
                    if (dataStore.sales_area!![j].value.lowercase().contains(
                            charSequence.toString().lowercase(
                            )
                        )
                    ) {
                        objectListNew.add(dataStore.sales_area!![j])

                    }
                }
                areaAdapter.upDateList(objectListNew)
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    override fun clickOnArea(
        areaResponse: com.example.greenlight.database.AreaResponse
    ) {
        str = areaResponse?.name
        Log.d("TAG", "onViewCreateclickOnArea: " + str)

        if (areaResponse.name.equals("sales_country")) {
            binding.textPer.text = areaResponse.value + "  Performance"
            dataStore.sales_zone?.let { areaAdapter.upDateList(it) }
        } else if (areaResponse.name.equals("sales_zone")) {
            binding.textPer.text = areaResponse.value + "  Performance"
            dataStore.sales_region?.let { areaAdapter.upDateList(it) }
        } else if (areaResponse.name.equals("sales_region")) {
            binding.search.visibility = View.VISIBLE
//            binding.search.setQueryHint("Search by Name");
            binding.textPer.text = areaResponse.value + "  Performance"
            dataStore.sales_area?.let { areaAdapter.upDateList(it) }
        }
//        else {
//            binding.search.visibility = View.VISIBLE
//            binding.search.setQueryHint("Search by Name");
//            binding.textPer.text = areaResponse.value + "  Performance"
//
//        }

//        Toast.makeText(context,"Cell clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onStrValue(str: String) {
        this.str = str

    }


}