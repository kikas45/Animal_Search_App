package com.example.imageloadingwithpaging3.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imageloadingwithpaging3.R
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhoto
import com.example.imageloadingwithpaging3.databinding.FragmentSavedBinding
import com.example.imageloadingwithpaging3.mb.SavedViewModel
import com.example.imageloadingwithpaging3.ui.gallery.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SavedFragment : Fragment(R.layout.fragment_saved) , ProductsAdapter.OnItemClickListenerMe {

    private val mUserViewModel by viewModels<SavedViewModel>()

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSavedBinding.bind(view)

        val adapter = DetailAdapter()
        binding.apply {
            recyclerview.adapter = adapter
            recyclerview.layoutManager = LinearLayoutManager(requireContext())

            backArrowPre.setOnClickListener {
                view.findNavController().popBackStack(R.id.galleryFragment, false)

            }
        }


        mUserViewModel.allNotes.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onResume() {
        super.onResume()
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.findNavController()?.popBackStack(R.id.galleryFragment, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }

    override fun onItemClickedMe(photo: UnsplashPhoto) {
        val bundle = Bundle().apply {

            putString("id", photo.id.toString())
            putString("urls_image", photo.urls.small.toString())
            putString("user_name", photo.user.username.toString())
            putString("description", photo.description.toString())
        }

        view?.findNavController()?.navigate(R.id.action_galleryFragment_to_detailsFragment, bundle)

    }

}

