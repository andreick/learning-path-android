package com.example.animals.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.animals.databinding.ItemAnimalBinding
import com.example.animals.model.Animal

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

    class ViewHolder(private val binding: ItemAnimalBinding) : RecyclerView.ViewHolder(binding.root),
        AnimalClickListener {

        fun bind(animal: Animal) {
            binding.animal = animal
            binding.listener = this
        }

        override fun onClick(animal: Animal) {
            val action = AnimalListFragmentDirections.actionShowDetail(animal)
            itemView.findNavController().navigate(action)
        }
    }
}