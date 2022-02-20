package com.andreick.contactsprovider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreick.contactsprovider.databinding.ItemContactBinding

class ContactAdapter(private val contactLst: List<Contact>) :
    RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bindItem(contactLst[position])
    }

    override fun getItemCount(): Int = contactLst.size
}

class ContactViewHolder(private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItem(contact: Contact) {
        binding.tvContactName.text = contact.name
        binding.tvPhone.text = contact.phoneNumber
    }
}