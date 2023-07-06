package com.example.imageloadingwithpaging3.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.imageloadingwithpaging3.R
import com.example.imageloadingwithpaging3.databinding.FragmentGalleryBinding
import com.example.imageloadingwithpaging3.databinding.FragmentSearchHistoryBinding


class SearchHistoryFragment : Fragment(R.layout.fragment_search_history) {

    private var _binding: FragmentSearchHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchHistoryBinding.bind(view)

        binding.apply {
            backArrowPre.setOnClickListener {
                view.findNavController().popBackStack(R.id.galleryFragment, false)
            }
        }


    }

}