package com.example.imageloadingwithpaging3.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository,
   // savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
 /// private val currentQuery = savedStateHandle.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val photos = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    companion object {
       //  private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "cats"
    }

}