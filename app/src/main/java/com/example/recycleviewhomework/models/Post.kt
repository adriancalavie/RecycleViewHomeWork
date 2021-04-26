package com.example.recycleviewhomework.models

import com.example.recycleviewhomework.interfaces.IExpandable

class Post(val title: String, val body: String): IExpandable {
    override fun getExpandableType(): ExpandableType = ExpandableType.UNDER_ITEM
}