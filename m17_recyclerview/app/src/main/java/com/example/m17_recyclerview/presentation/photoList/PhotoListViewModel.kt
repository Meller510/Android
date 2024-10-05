package com.example.m17_recyclerview.presentation.photoList


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.m17_recyclerview.data.MarsPhotoPagingSource
import com.example.m17_recyclerview.domain.GetMarsPhotoUseCase
import com.example.m17_recyclerview.entity.MarsPhoto

import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class PhotoListViewModel @Inject constructor(
    private val getMarsPhotoUseCase: GetMarsPhotoUseCase
) : ViewModel() {
    val pagedPhotos: Flow<PagingData<MarsPhoto>> = Pager(
        PagingConfig(10),
        pagingSourceFactory = { MarsPhotoPagingSource(getMarsPhotoUseCase.apiService()) }
    ).flow.cachedIn(viewModelScope)
}