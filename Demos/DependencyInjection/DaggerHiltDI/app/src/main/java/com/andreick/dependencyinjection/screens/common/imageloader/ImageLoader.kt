package com.andreick.dependencyinjection.screens.common.imageloader

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.andreick.dependencyinjection.common.dependencyinjection.activity.ActivityScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject

@ActivityScope
class ImageLoader @Inject constructor(private val activity: AppCompatActivity) {

    private val requestOptions = RequestOptions().centerCrop()

    fun loadImage(imageUrl: String, target: ImageView) {
        Glide.with(activity)
            .load(imageUrl)
            .apply(requestOptions)
            .into(target)
    }
}