package com.example.koakptutorial2.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.koakptutorial2.R
import com.example.koakptutorial2.db.entities.Todo

class ToDoAdapter(private val mOnTapView : OnTapView)
    : androidx.recyclerview.widget.ListAdapter<Todo,ToDoAdapter.ToDoViewHolder>(
    object :DiffUtil.ItemCallback<Todo>()
    {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem==newItem
        }

    }
)
   {


    interface OnTapView{
        fun onTapDelete(todo : Todo)
    }
    class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTodo: TextView = itemView.findViewById<TextView>(R.id.tvTodo)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        val holder = ToDoViewHolder(view)

        holder.ivDelete.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION){
                val itemDel = currentList[position]
            mOnTapView.onTapDelete(itemDel)
        }
        }

        return holder


    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val item = currentList[position]
        holder.tvTodo.text = item.text
    }


}