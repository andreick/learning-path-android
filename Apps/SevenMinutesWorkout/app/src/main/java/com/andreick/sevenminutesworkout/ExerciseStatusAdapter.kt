package com.andreick.sevenminutesworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.andreick.sevenminutesworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(private val exerciseList: List<Exercise>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemExerciseStatusBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exerciseList[position]

        with(holder.binding) {
            tvExerciseItem.text = exercise.id.toString()

            when {
                exercise.isSelected -> {
                    tvExerciseItem.background = ContextCompat.getDrawable(
                        holder.itemView.context, R.drawable.item_circular_thin_color_accent_border
                    )
                    tvExerciseItem.setTextColor(ContextCompat.getColor(
                        holder.itemView.context, R.color.color_primary
                    ))
                }
                exercise.isCompleted -> {
                    tvExerciseItem.background = ContextCompat.getDrawable(
                        holder.itemView.context, R.drawable.item_circular_color_accent_bg
                    )
                    tvExerciseItem.setTextColor(ContextCompat.getColor(
                        holder.itemView.context, R.color.white
                    ))
                }
            }
        }
    }

    override fun getItemCount(): Int = exerciseList.size

    class ViewHolder(val binding: ItemExerciseStatusBinding) : RecyclerView.ViewHolder(binding.root)
}