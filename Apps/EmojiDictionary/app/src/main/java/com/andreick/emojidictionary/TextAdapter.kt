package com.andreick.emojidictionary

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class TextAdapter(private val emojis: ArrayList<String>) : RecyclerView.Adapter<TextAdapter.TextHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextHolder {
        return TextHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyckerview_item_row, parent, false))
    }

    override fun onBindViewHolder(holder: TextHolder, position: Int) {
        holder.bingEmoji(emojis[position])
    }

    override fun getItemCount(): Int {
        return emojis.count()
    }

    class TextHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var emoji: String = ""

        init {
            v.setOnClickListener(this)
        }

        fun bingEmoji(emoji: String) {
            this.emoji = emoji
            val itemTextView = view.findViewById<TextView>(R.id.itemTextView)
            itemTextView.text = emoji
        }

        override fun onClick(v: View?) {
            val detailIntent = Intent(view.context, TextDetailActivity::class.java)
            detailIntent.putExtra("emoji", emoji)
            startActivity(view.context, detailIntent, null)
        }
    }
}