package com.example.m17_recyclerview.presentation.photoList

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.m17_recyclerview.databinding.FragmentPhotoListBinding
import com.example.m17_recyclerview.di.DaggerAppComponent
import com.example.m17_recyclerview.presentation.photoList.recyclerView.PhotoPageAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class PhotoListFragment : Fragment() {
    private var _binding: FragmentPhotoListBinding? = null
    private val mBinding get() = _binding!!
    private val photoPageAdapter = PhotoPageAdapter()
    private val viewModel: PhotoListViewModel by viewModels {
        DaggerAppComponent.create().photoListModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoListBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        mBinding.recyclerView.adapter = photoPageAdapter

        viewModel.pagedPhotos.onEach {
            photoPageAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        mBinding.swipeRefresh.setOnRefreshListener { photoPageAdapter.refresh() }

        photoPageAdapter.loadStateFlow.onEach {
            mBinding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        mBinding.recyclerView.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}