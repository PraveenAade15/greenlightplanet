package com.example.greenlight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.greenlight.R
import com.example.greenlight.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        initClick()
        return binding.root
    }

    private fun initClick() {
        binding.btnPerform.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_zoneFragment)
        }
        binding.back.setOnClickListener {

        }
    }
}