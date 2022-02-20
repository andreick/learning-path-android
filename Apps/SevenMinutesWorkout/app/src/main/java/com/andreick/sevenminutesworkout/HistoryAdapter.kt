package com.andreick.sevenminutesworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.andreick.sevenminutesworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val dateList: ArrayList<String>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val date = dateList[position]

        with(holder.binding) {
            tvPosition.text = (position + 1).toString()
            tvHistoryItem.text = date

            tvPosition.text = (position + 1).toString()
            tvHistoryItem.text = date

            holder.binding.tvPosition

            if (position % 2 == 0) {
                clHistoryItemMain.setBackgroundColor(ContextCompat.getColor(context, R.color.slightly_gray))
            } else {
                clHistoryItemMain.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
        }
    }

    override fun getItemCount(): Int = dateList.size

    class ViewHolder(val binding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root)
}