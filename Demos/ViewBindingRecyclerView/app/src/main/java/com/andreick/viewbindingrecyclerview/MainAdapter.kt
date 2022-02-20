package com.andreick.viewbindingrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreick.viewbindingrecyclerview.databinding.TaskItemBinding

class MainAdapter(private val taskList: List<Task>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindTask(taskList[position])
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class MainViewHolder(private val taskBinding: TaskItemBinding) :
        RecyclerView.ViewHolder(taskBinding.root) {

        fun bindTask(task: Task) {
            taskBinding.tvTitle.text = task.title
            taskBinding.tvTime.text = task.timestamp
        }
    }
}