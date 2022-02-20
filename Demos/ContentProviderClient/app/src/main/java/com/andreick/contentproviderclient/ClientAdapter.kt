package com.andreick.contentproviderclient

import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreick.contentproviderclient.databinding.ItemClientBinding

class ClientAdapter(private val cursor: Cursor) : RecyclerView.Adapter<ClientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemClientBinding.inflate(inflater, parent, false)
        return ClientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        cursor.moveToPosition(position)
        holder.binding.tvClientItemTitle.text =
            cursor.getString(cursor.getColumnIndexOrThrow("title"))
        holder.binding.tvClientItemDescription.text =
            cursor.getString(cursor.getColumnIndexOrThrow("description"))
    }

    override fun getItemCount(): Int = cursor.count
}

class ClientViewHolder(val binding: ItemClientBinding) : RecyclerView.ViewHolder(binding.root)