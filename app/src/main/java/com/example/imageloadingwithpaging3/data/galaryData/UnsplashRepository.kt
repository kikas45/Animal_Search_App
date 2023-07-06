package com.example.imageloadingwithpaging3.data.galaryData

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.imageloadingwithpaging3.api.UnsplashApi
import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
               // pageSize = 20,
                pageSize = 3,
              //  maxSize = 100,
                maxSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query) }
        ).liveData
}