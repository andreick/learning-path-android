package com.example.animals.view

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.animals.databinding.FragmentAnimalDetailBinding
import com.example.animals.model.Animal
import com.example.animals.util.getProgressDrawable
import com.example.animals.util.loadImage

class AnimalDetailFragment : Fragment() {

    private lateinit var binding: FragmentAnimalDetailBinding
    private lateinit var animal: Animal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            animal = AnimalDetailFragmentArgs.fromBundle(it).animal
        }
        if (!::animal.isInitialized) return
        setAnimalDetails()
        animal.imageUrl?.let { setBackgroundColor(it) }
    }

    private fun setAnimalDetails() {
        with(binding) {
            ivDetailAnimal.loadImage(animal.imageUrl, context?.getProgressDrawable())
            tvDetailAnimalName.text = animal.name
            tvDetailAnimalLocation.text = animal.location
            tvDetailAnimalLifeSpan.text = animal.lifeSpan
            tvDetailAnimalDiet.text = animal.diet
        }
    }

    private fun setBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: Color.WHITE
                            binding.clMain.setBackgroundColor(intColor)
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}