package com.example.imageloadingwithpaging3.api

import com.example.imageloadingwithpaging3.data.galaryData.UnsplashPhoto


data class UnsplashResponse(
    val results: List<UnsplashPhoto>
)