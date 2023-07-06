package com.example.imageloadingwithpaging3.ui.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.LoadState
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

        binding.apply {

            imagSearch.setOnClickListener {

          view.findNavController().navigate(R.id.action_galleryFragment_to_searchHistoryFragment)
            }


            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null

            // retry for head and footer
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header =  UnsplashPhotoLoadStateAdapter{adapter.retry()},
                footer =   UnsplashPhotoLoadStateAdapter{adapter.retry()}

            )

            // btn retry from Frgament UI
            buttonRetry.setOnClickListener { adapter.retry() }

        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState->
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

        setHasOptionsMenu(true)
    }


    override fun onItemClicked(photo: UnsplashPhoto) {
        val bundle = Bundle()
        bundle.putString("urls", photo.urls.toString())
        bundle.putString("user", photo.user.toString())
        bundle.putString("description", photo.description.toString())
        bundle.putString("id", photo.id.toString())
    ///    view?.findNavController()?.navigate(R.id.action_galleryFragment_to_detailsFragment, bundle)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

