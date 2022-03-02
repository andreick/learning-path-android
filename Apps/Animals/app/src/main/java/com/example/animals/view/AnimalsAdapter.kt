package com.example.animals.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animals.databinding.ItemAnimalBinding
import com.example.animals.model.Animal
import com.example.animals.util.getProgressDrawable
import com.example.animals.util.loadImage

class AnimalsAdapter(
    private val animalList: ArrayList<Animal> = arrayListOf()
) : RecyclerView.Adapter<AnimalsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemAnimalBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(animalList[position])
    }

    override fun getItemCount(): Int = animalList.size

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemAnimalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(animal: Animal) {
            with(binding) {
                tvAnimalName.text = animal.name
                ivAnimal.loadImage(animal.imageUrl, itemView.context.getProgressDrawable())
            }
        }
    }
}