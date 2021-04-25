package com.example.recycleviewhomework.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recycleviewhomework.R
import com.example.recycleviewhomework.adapters.ExpandableAdapter
import com.example.recycleviewhomework.interfaces.IActivityFragmentCommunication
import com.example.recycleviewhomework.models.User
import kotlinx.android.synthetic.main.fragment_users.*

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

        val users = ArrayList<User>()

        users.add(User(1, "Adi1"))
        users.add(User(2, "Adi2"))
        users.add(User(3, "Adi3"))
        users.add(User(4, "Adi4"))
        users.add(User(5, "Adi5"))
        users.add(User(6, "Adi6"))
        users.add(User(7, "Adi7"))
        users.add(User(8, "Adi8"))
        users.add(User(9, "Adi9"))
        users.add(User(10, "Adi10"))
        users.add(User(11, "Adi11"))

        recycler_view.adapter = ExpandableAdapter(users)
        recycler_view.layoutManager = LinearLayoutManager(this.context)

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