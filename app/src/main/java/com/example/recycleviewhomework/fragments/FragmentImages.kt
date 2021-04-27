package com.example.recycleviewhomework.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.recycleviewhomework.R
import com.example.recycleviewhomework.adapters.PhotoAdapter
import com.example.recycleviewhomework.interfaces.IActivityFragmentCommunication
import com.example.recycleviewhomework.models.Photo
import com.example.recycleviewhomework.utils.Constants.ALBUMS_URL
import com.example.recycleviewhomework.utils.Constants.BASE_URL
import com.example.recycleviewhomework.utils.Constants.IMAGE_URL
import com.example.recycleviewhomework.utils.Constants.PHOTOS_URL
import com.example.recycleviewhomework.utils.VolleySingleton
import kotlinx.android.synthetic.main.fragment_images.*
import kotlinx.android.synthetic.main.fragment_images.view.*
import org.json.JSONArray

class FragmentImages(private val userId: Int) : Fragment() {

    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var activity: IActivityFragmentCommunication? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_images, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = view.refresh_images
        swipeRefreshLayout!!.setOnRefreshListener {
            doUpdate()
            swipeRefreshLayout!!.isRefreshing = false
        }

        doUpdate()
    }

    private fun doUpdate() {
        if (userId != -1) {

            val url = "$BASE_URL/$ALBUMS_URL/$userId/$PHOTOS_URL/"

            val requestQ = VolleySingleton.getInstance(context!!).requestQueue

            val photosRequest = StringRequest(
                Request.Method.GET,
                url,
                { response: String? ->
                    val photos = getPhotosFromJSON(JSONArray(response))
                    photogrid_recycler_view.adapter = PhotoAdapter(photos)
                    photogrid_recycler_view.layoutManager =
                        GridLayoutManager(this.context, 3, RecyclerView.VERTICAL, false)
                },
                { _ ->
                    val photos = ArrayList<Photo>()
                    photogrid_recycler_view.adapter = PhotoAdapter(photos)
                    photogrid_recycler_view.layoutManager =
                        GridLayoutManager(this.context, 3, RecyclerView.VERTICAL, false)
                }
            )

            requestQ.add(photosRequest)
        }
    }

    private fun getPhotosFromJSON(jsonArray: JSONArray): ArrayList<Photo> {
        val photos = ArrayList<Photo>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val url = jsonObject.optString(IMAGE_URL)
            photos.add(Photo(url))
        }

        return photos
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IActivityFragmentCommunication) {
            this.activity = context
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentImages(-1)
    }
}