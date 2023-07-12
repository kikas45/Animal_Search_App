package com.example.imageloadingwithpaging3.ui.details

import android.os.Bundle
import android.view.View

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

import com.example.imageloadingwithpaging3.R
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhoto
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhotoUrls
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashUser
import com.example.imageloadingwithpaging3.databinding.FragmentDetailsBinding
import com.example.imageloadingwithpaging3.mb.SavedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var mUserViewModel: SavedViewModel


    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailsBinding.bind(view)

        mUserViewModel = ViewModelProvider(this).get(SavedViewModel::class.java)

        val id = arguments?.getString("id").toString()
        val urls_image = arguments?.getString("urls_image").toString()
        val user_name = arguments?.getString("user_name").toString()
        val description = arguments?.getString("description").toString()




        binding.apply {

                context?.let {
                    Glide.with(it)
                        .load(urls_image.toString())
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_launcher_background)
                        .into(imageView)
                }

            textViewDescription.text = description.toString()
            textViewCreator.text = user_name.toString()





                backArrowPre.setOnClickListener {
                view.findNavController().popBackStack(R.id.galleryFragment, false)
            }

            fabBtn.setOnClickListener {
                insertDataToDatabase(id, urls_image, user_name, description )
            }

        }

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

    private fun insertDataToDatabase(id: String,urls_small: String,custom_name:String, description:String) {

        val url_image = UnsplashPhotoUrls("", "", "", "", "")
        val user_name = UnsplashUser("","")

        val user = UnsplashPhoto(  id, description, url_image, user_name, urls_small ,custom_name )
        // Add Data to Database
        mUserViewModel.insert(user)

    }


}