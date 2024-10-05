package com.example.m17_recyclerview.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.m17_recyclerview.entity.MarsPhoto
import javax.inject.Inject

class MarsPhotoPagingSource @Inject constructor(
    private val repository: Repository
) : PagingSource<Int, MarsPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, MarsPhoto>) = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarsPhoto> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getMarsPhotos(page).photos
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    it, null,
                    if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 0
    }
}