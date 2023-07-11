package com.example.imageloadingwithpaging3.ui.gallery

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imageloadingwithpaging3.R
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhoto
import com.example.imageloadingwithpaging3.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery), ProductsAdapter.OnItemClickListener {


    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)


        val adapter = ProductsAdapter(this)

        val sharedDass = view.context.applicationContext.getSharedPreferences(
            "PASS_DATA_TRANS_FRAGMENT",
            Context.MODE_PRIVATE
        )
        val editor = sharedDass.edit()


        binding.apply {


            btnFavorite.setOnClickListener {
                view.findNavController()
                    .navigate(R.id.action_galleryFragment_to_savedFragment)

            }

            imagSearch.setOnClickListener {

                view.findNavController()
                    .navigate(R.id.action_galleryFragment_to_searchHistoryFragment)
                editor.clear()

            }


            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.itemAnimator = null
            // retry for head and footer
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { adapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { adapter.retry() }

            )

            // btn retry from Frgament UI
            buttonRetry.setOnClickListener { adapter.retry() }

        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error


                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }

            }
        }

    }


    override fun onItemClicked(photo: UnsplashPhoto) {
        val bundle = Bundle().apply {

            putString("id", photo.id.toString())
            putString("urls_image", photo.urls.small.toString())
            putString("user_name", photo.user.username.toString())
            putString("description", photo.description.toString())
        }

        view?.findNavController()?.navigate(R.id.action_galleryFragment_to_detailsFragment, bundle)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onResume() {
        super.onResume()
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
              activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }
}




