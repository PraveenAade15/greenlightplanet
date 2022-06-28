package com.example.greenlight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.greenlight.R
import com.example.greenlight.databinding.FragmentTapBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TapFragment : Fragment() {
    private var _binding: FragmentTapBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTapBinding.inflate(inflater, container, false)
        initClick();
        return binding.root
    }

    private fun initClick() {
        binding.ivTap.setOnClickListener {
            findNavController().navigate(R.id.action_tapFragment_to_homeFragment)
        }
    }


}