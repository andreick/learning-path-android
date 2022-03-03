package com.andreick.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.andreick.room.databinding.ItemsRowBinding

class ItemAdapter(
    private val items: ArrayList<EmployeeEntity>,
    private val updateListener: (id: Int) -> Unit,
    private val deleteListener: (id: Int) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemsRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]

        holder.tvName.text = item.name
        holder.tvEmail.text = item.email

        if (position % 2 == 0) {
            holder.clItemsRow.setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray))
        } else {
            holder.clItemsRow.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        holder.ivEdit.setOnClickListener { updateListener(item.id) }
        holder.ivDelete.setOnClickListener { deleteListener(item.id) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(binding: ItemsRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val clItemsRow = binding.clItemsRow
        val tvName = binding.tvName
        val tvEmail = binding.tvEmail
        val ivEdit = binding.ivEdit
        val ivDelete = binding.ivDelete
    }
}