package com.artemissoftware.githubprofile.ui.databinding

import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("setImage")
fun setImage(imageView: ShapeableImageView, image: Int) {
    Glide.with(imageView.context)
        .load(image)
        .into(imageView)
}

@BindingAdapter("setImage")
fun setImage(imageView: ShapeableImageView, image: String?) {
    image?.let {
        Glide.with(imageView.context)
            .load(it)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(imageView)
    }
}

@BindingAdapter("setImage")
fun setImage(imageView: ImageView, image: String?) {

    image?.let {
        Glide.with(imageView.context)
            .load(it)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(imageView)
    }

}

@BindingAdapter("setTint")
fun setImageTint(imageView: ImageView, tint: String?){

    tint?.let {
        imageView.setColorFilter(Color.parseColor(it), PorterDuff.Mode.SRC_IN)
    }

}