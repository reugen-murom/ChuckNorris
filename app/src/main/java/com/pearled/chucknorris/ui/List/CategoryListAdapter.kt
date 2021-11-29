package com.pearled.chucknorris.ui.List

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pearled.chucknorris.databinding.ItemCategoryBinding

class CategoryListAdapter(val clickListener: OnCategoryClickListener?) : ListAdapter<String, CategoryListAdapter.ItemViewholder>(DiffCallback())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val itemBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewholder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryListAdapter.ItemViewholder, position: Int) {
        val currentItem = getItem(position)
        holder.itemBinding.itemCategoryName.text = currentItem
        holder.itemView.setOnClickListener {
            clickListener?.onCategoryClick(currentItem)
        }
    }

    class ItemViewholder(val itemBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}

class DiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

interface OnCategoryClickListener {
    fun onCategoryClick(category: String)
}