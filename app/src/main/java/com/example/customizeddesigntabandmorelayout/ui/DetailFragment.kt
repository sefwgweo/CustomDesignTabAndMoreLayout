package com.example.customizeddesigntabandmorelayout.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.customizeddesigntabandmorelayout.R
import com.example.customizeddesigntabandmorelayout.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    companion object {
        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

    private lateinit var binding: FragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
    }
}