package com.example.recycleviewhomework.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleviewhomework.MainActivity
import com.example.recycleviewhomework.R
import com.example.recycleviewhomework.fragments.FragmentAlbums
import com.example.recycleviewhomework.fragments.FragmentImages
import com.example.recycleviewhomework.interfaces.IActivityFragmentCommunication
import com.example.recycleviewhomework.models.Album
import kotlinx.android.synthetic.main.vanilla_rv_item.view.*

class VanillaAdapter(private val albums: ArrayList<Album>,
                     private val activity: IActivityFragmentCommunication?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.vanilla_text_view
        var id = -1
        fun bind(album: Album) {
            titleView.text = album.title
            id = album.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vanillaRvItemView = inflater.inflate(R.layout.vanilla_rv_item, parent, false)
        return AlbumViewHolder(vanillaRvItemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = albums[position]
        (holder as AlbumViewHolder).itemView.setOnClickListener {
            val myFragment: Fragment = FragmentImages(holder.id)
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, myFragment).addToBackStack(null).commit()
        }
        holder.bind(currentItem)
    }

    override fun getItemCount() = albums.size

}
