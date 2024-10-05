package com.example.m17_recyclerview.presentation.detailedPhoto

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.m17_recyclerview.R
import com.example.m17_recyclerview.databinding.FragmentDetailedPhotoBinding


class DetailedPhotoFragment : Fragment() {
    private var _binding: FragmentDetailedPhotoBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition =
            TransitionInflater.from(context).inflateTransition(R.transition.my_move)
        transition.duration = 400
        sharedElementEnterTransition = transition

        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.my_move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedPhotoBinding.inflate(layoutInflater)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val transitionNameImage = bundle.getString(getString(R.string.image_transition))
        val transitionNameViewBorderText = bundle.getString(getString(R.string.rover_transition))
        val roverText = bundle.getString(getString(R.string.rover))

        mBinding.imageView.transitionName = transitionNameImage
        mBinding.textRoverName.transitionName = transitionNameViewBorderText
        mBinding.textRoverName.text = roverText

        Glide.with(mBinding.imageView).load(transitionNameImage)
            .transform(RoundedCorners(15))
            .transition(DrawableTransitionOptions.withCrossFade()).into(mBinding.imageView)
    }
}