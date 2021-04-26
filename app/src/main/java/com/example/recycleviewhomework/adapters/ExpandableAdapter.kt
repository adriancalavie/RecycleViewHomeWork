package com.example.recycleviewhomework.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.recycleviewhomework.MainActivity
import com.example.recycleviewhomework.R
import com.example.recycleviewhomework.fragments.FragmentAlbums
import com.example.recycleviewhomework.interfaces.IActivityFragmentCommunication
import com.example.recycleviewhomework.interfaces.IExpandable
import com.example.recycleviewhomework.models.ExpandableType
import com.example.recycleviewhomework.models.Post
import com.example.recycleviewhomework.models.User
import com.example.recycleviewhomework.utils.Constants.BASE_URL
import com.example.recycleviewhomework.utils.Constants.POSTS_URL
import com.example.recycleviewhomework.utils.Constants.POST_BODY
import com.example.recycleviewhomework.utils.Constants.POST_TITLE
import com.example.recycleviewhomework.utils.Constants.USERS_URL
import com.example.recycleviewhomework.utils.VolleySingleton
import kotlinx.android.synthetic.main.expandable_rv_item.view.*
import kotlinx.android.synthetic.main.rv_underitem_item.view.*
import org.json.JSONArray


class ExpandableAdapter(
    private var items: ArrayList<IExpandable>,
    private val activity: IActivityFragmentCommunication?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class TopViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var userId = -1
        private val nameView: TextView = itemView.item_text_view
        val btnNextView: ImageButton = itemView.btn_go_next

        fun bind(user: User) {
            nameView.text = user.name
            userId = user.id
        }
    }

    class UnderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.underitem_title
        private val bodyView: TextView = itemView.underitem_body

        fun bind(post: Post) {
            titleView.text = post.title
            bodyView.text = post.body
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position].getExpandableType() == ExpandableType.TOP_ITEM) {
            return 0
        } else if (items[position].getExpandableType() == ExpandableType.UNDER_ITEM) {
            return 1
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == ExpandableType.TOP_ITEM.ordinal) {
            val topItemView = inflater.inflate(R.layout.expandable_rv_item, parent, false)
            TopViewHolder(topItemView)
        } else {
            val underItemView = inflater.inflate(R.layout.rv_underitem_item, parent, false)
            UnderViewHolder(underItemView)
        }
    }

    private fun cleanItems(from: Int, delSize: Int) {

        for (i in 0 until delSize) {
            items.removeAt(from)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = items[position]

        if (holder is TopViewHolder) {
            val user = currentItem as User
            holder.itemView.setOnClickListener {
                if (!user.isExpanded) {

                    val url = "$BASE_URL/$USERS_URL/${holder.userId}/$POSTS_URL/"
                    val requestQ = VolleySingleton.getInstance(holder.itemView.context).requestQueue
                    if ((items[position] as User).posts.size == 0) {
                        val postsRequest = StringRequest(
                            Request.Method.GET,
                            url,
                            { responseString ->
                                val posts = getPostsFromRequestResponse(JSONArray(responseString))
                                for (i in 0 until posts.size) {
                                    items.add(holder.layoutPosition + 1 + i, posts[i])
                                }
                                (items[position] as User).posts = posts
                                notifyItemRangeInserted(holder.layoutPosition + 1, posts.size)
                            },
                            {
                                items.add(
                                    holder.layoutPosition + 1,
                                    Post("ERROR GETTING TITLE AND BODY", "")
                                )
                                notifyItemRangeInserted(holder.layoutPosition + 1, 1)
                            }
                        )
                        requestQ.add(postsRequest)
                    } else {
                        for (i in 0 until (items[position] as User).posts.size) {
                            items.add(
                                holder.layoutPosition + 1 + i,
                                (items[position] as User).posts[i]
                            )
                        }
                        notifyItemRangeInserted(
                            holder.layoutPosition + 1,
                            (items[position] as User).posts.size
                        )
                    }
                    user.isExpanded = true
                } else {

                    cleanItems(holder.layoutPosition + 1, (items[position] as User).posts.size)

                    notifyItemRangeRemoved(
                        holder.layoutPosition + 1,
                        (items[position] as User).posts.size
                    )
                    user.isExpanded = false
                }


            }
            holder.btnNextView.setOnClickListener {
                val myFragment: Fragment = FragmentAlbums(holder.userId)
                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, myFragment).addToBackStack(null).commit()
            }
            holder.bind(user)

        } else if (holder is UnderViewHolder) {

            val post = currentItem as Post
            holder.bind(post)
        }
    }

    private fun getPostsFromRequestResponse(jsonArray: JSONArray): ArrayList<Post> {
        val posts = ArrayList<Post>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val title = jsonObject.optString(POST_TITLE)
            val body = jsonObject.optString(POST_BODY)
            posts.add(Post(title, body))
        }

        return posts
    }

    override fun getItemCount() = items.size
}