package com.example.imageloadingwithpaging3.ui.search.onItemSearched

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imageloadingwithpaging3.R
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhoto
import com.example.imageloadingwithpaging3.databinding.FragmentSearchBinding
import com.example.imageloadingwithpaging3.ui.gallery.HistoryViewModel
import com.example.imageloadingwithpaging3.ui.gallery.ProductsAdapter
import com.example.imageloadingwithpaging3.ui.gallery.UnsplashPhotoLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), ProductsAdapter.OnItemClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<HistoryViewModel>()

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        val adapter = ProductsAdapter(this)

        val sharedDass = view.context.applicationContext.getSharedPreferences("PASS_DATA_TRANS_FRAGMENT", Context.MODE_PRIVATE)
        val editor = sharedDass.edit()
        val urlData = sharedDass?.getString("search", "")
        val urls = arguments?.getString("search").toString()

        if (urlData  == "SavedData"){
            binding.recyclerView.scrollToPosition(0)
            viewModel.searchPhotos_History(urls)
            binding.titleText.text = urls
        }else{
            binding.titleText.text = urls
        }


        binding.apply {

            imagSearch.setOnClickListener {
                view.findNavController()
                    .navigate(R.id.action_searchFragment_to_searchHistoryFragment)
                editor.clear()
                editor.apply()

            }

            backArrowPre.setOnClickListener {
                view.findNavController().popBackStack(R.id.galleryFragment, false)
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
        val bundle = Bundle()
        bundle.putString("urls", photo.urls.toString())
        bundle.putString("user", photo.user.toString())
        bundle.putString("description", photo.description.toString())
        bundle.putString("id", photo.id.toString())
         view?.findNavController()?.navigate(R.id.action_searchFragment_to_detailsFragment, bundle)
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

}