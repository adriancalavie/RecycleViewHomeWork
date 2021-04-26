package com.example.recycleviewhomework.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.recycleviewhomework.R
import com.example.recycleviewhomework.adapters.VanillaAdapter
import com.example.recycleviewhomework.interfaces.IActivityFragmentCommunication
import com.example.recycleviewhomework.interfaces.IExpandable
import com.example.recycleviewhomework.models.Album
import com.example.recycleviewhomework.models.User
import com.example.recycleviewhomework.utils.Constants
import com.example.recycleviewhomework.utils.Constants.ALBUMS_URL
import com.example.recycleviewhomework.utils.Constants.ALBUM_ID
import com.example.recycleviewhomework.utils.Constants.ALBUM_TITLE
import com.example.recycleviewhomework.utils.Constants.BASE_URL
import com.example.recycleviewhomework.utils.Constants.USERS_URL
import com.example.recycleviewhomework.utils.VolleySingleton
import kotlinx.android.synthetic.main.fragment_albums.*
import org.json.JSONArray

class FragmentAlbums(private val userId: Int) : Fragment() {

    private var activity: IActivityFragmentCommunication? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (userId != -1) {

            val url = "$BASE_URL/$USERS_URL/$userId/$ALBUMS_URL/"

            val requestQ = VolleySingleton.getInstance(context!!).requestQueue

            val albumsRequest = StringRequest(
                Request.Method.GET,
                url,
                { response: String? ->
                    val albums = getAlbumsFromJSON(JSONArray(response))

                    vanilla_recycler_view.adapter = VanillaAdapter(albums, activity)
                    vanilla_recycler_view.layoutManager = LinearLayoutManager(this.context)
                },
                { volleyError ->

                    val albums = ArrayList<Album>()
                    albums.add(Album("$volleyError", -1))

                    vanilla_recycler_view.adapter = VanillaAdapter(albums, activity)
                    vanilla_recycler_view.layoutManager = LinearLayoutManager(this.context)
                }
            )

            requestQ.add(albumsRequest)
        }
    }

    private fun getAlbumsFromJSON(jsonArray: JSONArray): ArrayList<Album> {
        val albums = ArrayList<Album>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val name = jsonObject.optString(ALBUM_TITLE)
            val id = jsonObject.optString(ALBUM_ID).toInt()
            albums.add(Album(name, id))
        }

        return albums
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IActivityFragmentCommunication) {
            this.activity = context
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentAlbums(-1)
    }
}