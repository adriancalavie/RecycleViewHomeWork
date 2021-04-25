package com.example.recycleviewhomework.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleviewhomework.R
import com.example.recycleviewhomework.models.User
import kotlinx.android.synthetic.main.rv_item.view.*

class ExpandableAdapter(private val users: List<User>) :
    RecyclerView.Adapter<ExpandableAdapter.ExpandableViewHolder>() {

    class ExpandableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.item_text_view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)

        return ExpandableViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpandableViewHolder, position: Int) {
        val currentItem = users[position]

        holder.textView.text = currentItem.name
    }

    override fun getItemCount() = users.size
}