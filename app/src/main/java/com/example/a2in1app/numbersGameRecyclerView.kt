package com.example.a2in1app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.view.*

class numbersGameRecyclerView (private val messages: ArrayList<String>): RecyclerView.Adapter<numbersGameRecyclerView.ItemViewHolder>() {
    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val message = messages[position]
        holder.itemView.apply {
            textViewRowItem.text = message
        }
    }

    override fun getItemCount() = messages.size

}