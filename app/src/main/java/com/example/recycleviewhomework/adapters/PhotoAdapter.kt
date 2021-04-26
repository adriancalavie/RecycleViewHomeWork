package com.example.recycleviewhomework.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.android.volley.toolbox.ImageLoader
import com.example.recycleviewhomework.R
import com.example.recycleviewhomework.models.Photo
import com.example.recycleviewhomework.utils.VolleySingleton
import kotlinx.android.synthetic.main.photo_rv_item.view.*

class PhotoAdapter(private val photos: ArrayList<Photo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photoView: ImageView = itemView.grid_photo_view

        fun bind(photo: Photo) {
            val url = photo.url + ".png"

            photoView.load(url){
                crossfade(true)
                transformations(RoundedCornersTransformation(30f))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val photoItemView = inflater.inflate(R.layout.photo_rv_item, parent, false)
        return PhotoViewHolder(photoItemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = photos[position]
        (holder as PhotoViewHolder).bind(currentItem)
    }

    override fun getItemCount() = photos.size

}
