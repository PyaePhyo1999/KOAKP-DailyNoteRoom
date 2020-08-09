package com.example.koakptutorial2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.koakptutorial2.R
import com.example.koakptutorial2.db.entities.Category
import com.example.koakptutorial2.db.entities.Todo

class CategoryAdapter(private val mOnTapView: OnTapView) :
    androidx.recyclerview.widget.ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(
        object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }
        }
    ) {
    interface OnTapView {
        fun onTapCategory(category :Category)
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategory: TextView = itemView.findViewById<TextView>(R.id.tvCategory)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        val holder = CategoryViewHolder(view)

        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION){
                val itemTap = currentList[position]
                mOnTapView.onTapCategory(itemTap)
            }
        }

        return holder


    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = currentList[position]
        holder.tvCategory.text = item.name
    }
}
