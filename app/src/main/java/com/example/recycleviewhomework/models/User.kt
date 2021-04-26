package com.example.recycleviewhomework.models

import com.example.recycleviewhomework.interfaces.IExpandable


data class User(val id: Int, val name: String):IExpandable {

    override fun getExpandableType(): ExpandableType = ExpandableType.TOP_ITEM
    var posts: ArrayList<Post> = ArrayList<Post>()
    var isExpanded = false
}