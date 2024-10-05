package com.example.m17_recyclerview.presentation.photoList.recyclerView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.m17_recyclerview.R
import com.example.m17_recyclerview.databinding.PhotoListItemBinding
import com.example.m17_recyclerview.entity.MarsPhoto


class PhotoPageAdapter : PagingDataAdapter<MarsPhoto, PhotoViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = PhotoListItemBinding.inflate(LayoutInflater.from(parent.context))
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            initPhotoItem(holder.binding, it, position)
            onClickItem(holder.binding, it)
        }
    }

    private fun imageLoad(imageView: ImageView, item: MarsPhoto?) {
        Glide.with(imageView).load(item?.imgSrc)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(RoundedCorners(15)).into(imageView)
    }

    private fun initPhotoItem(binding: PhotoListItemBinding, item: MarsPhoto?, position: Int) {
        imageLoad(binding.imageView, item)

        val context = binding.root.context
        with(binding) {
            txtCameraName.text =
                context.getString(R.string.camera, item?.camera?.name.toString())
            txtDate.text = context.getString(R.string.date, item?.earthDate.toString())
            txtSol.text = context.getString(R.string.sol, item?.sol.toString())
            txtInfo.text = context.getString(R.string.rover, item?.rover?.name)
            imageView.transitionName = item?.imgSrc
            txtInfo.transitionName =
                context.getString(R.string.rover_transition) + position
        }
    }

    private fun onClickItem(binding: PhotoListItemBinding, item: MarsPhoto?) {
        val context = binding.root.context
        binding.cardView.setOnClickListener {
            with(binding) {
                val bundle = Bundle().apply {

                    putString(context.getString(R.string.image_transition), item?.imgSrc)
                    putString(
                        context.getString(R.string.rover_transition),
                        txtInfo.transitionName
                    )
                    putString(
                        context.getString(R.string.rover),
                        txtInfo.text.toString() + "\n" + txtCameraName.text + "\n" + txtDate.text
                    )
                }

                val extras = FragmentNavigatorExtras(
                    imageView to item!!.imgSrc,
                    txtInfo to txtInfo.transitionName,
                    viewTxtBoard to viewTxtBoard.transitionName
                )

                Navigation.findNavController(binding.root).navigate(
                    R.id.action_photoListFragment_to_detailedPhotoFragment,
                    bundle, null, extras
                )
            }
        }
    }
}


class PhotoViewHolder(val binding: PhotoListItemBinding) : RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<MarsPhoto>() {
    override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean =
        oldItem.imgSrc == newItem.imgSrc

    override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean =
        oldItem.imgSrc == newItem.imgSrc

}