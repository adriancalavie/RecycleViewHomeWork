package com.example.recycleviewhomework.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.recycleviewhomework.R
import com.example.recycleviewhomework.adapters.ExpandableAdapter
import com.example.recycleviewhomework.interfaces.IActivityFragmentCommunication
import com.example.recycleviewhomework.interfaces.IExpandable
import com.example.recycleviewhomework.models.User
import com.example.recycleviewhomework.utils.Constants.BASE_URL
import com.example.recycleviewhomework.utils.Constants.USERS_URL
import com.example.recycleviewhomework.utils.Constants.USER_ID
import com.example.recycleviewhomework.utils.Constants.USER_NAME
import com.example.recycleviewhomework.utils.VolleySingleton
import kotlinx.android.synthetic.main.fragment_users.*
import org.json.JSONArray

class FragmentUsers : Fragment() {

    private var activity: IActivityFragmentCommunication? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val url = "$BASE_URL/$USERS_URL/"

        val requestQ = VolleySingleton.getInstance(context!!).requestQueue

        val usersRequest = StringRequest(
            Request.Method.GET,
            url,
            { responseString ->
                expanded_recycler_view.adapter =
                    ExpandableAdapter(getUsersFromRequestResponse(JSONArray(responseString)), this.activity)
                expanded_recycler_view.layoutManager = LinearLayoutManager(this.context)
            },
            { volleyError ->
                val users = ArrayList<IExpandable>()

                users.add(User(1, "$volleyError"))

                expanded_recycler_view.adapter = ExpandableAdapter(users, this.activity)
                expanded_recycler_view.layoutManager = LinearLayoutManager(this.context)
            }
        )

        requestQ.add(usersRequest)
    }

    private fun getUsersFromRequestResponse(jsonArray: JSONArray): ArrayList<IExpandable> {
        val users = ArrayList<IExpandable>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.optString(USER_ID).toInt()
            val name = jsonObject.optString(USER_NAME)
            users.add(User(id, name))
        }

        return users
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IActivityFragmentCommunication) {
            this.activity = context
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentUsers()
    }
}