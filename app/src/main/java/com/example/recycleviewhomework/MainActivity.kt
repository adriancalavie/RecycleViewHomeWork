package com.example.recycleviewhomework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recycleviewhomework.fragments.FragmentAlbums
import com.example.recycleviewhomework.fragments.FragmentUsers
import com.example.recycleviewhomework.interfaces.IActivityFragmentCommunication

class MainActivity : AppCompatActivity(), IActivityFragmentCommunication {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(FragmentUsers::class.java.name)
    }

    override fun openNextActivity() {
    // EMPTY
    }

    override fun replaceFragment(tag: String) = when (tag) {
        FragmentUsers::class.java.name -> {
            addMainFragment()
        }
        FragmentAlbums::class.java.name ->{
            addSecondFragment()
        }
        else -> println("Invalid tag!")
    }

    private fun addSecondFragment() {
        val fragmentManager = this.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = FragmentAlbums::class.java.name
        val addTransaction = transaction.add(
            R.id.fragment_container, FragmentAlbums.newInstance(), tag
        )
        addTransaction.commit()
    }

    private fun addMainFragment() {
        val fragmentManager = this.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = FragmentUsers::class.java.name
        val addTransaction = transaction.add(
            R.id.fragment_container, FragmentUsers.newInstance(), tag
        )
        addTransaction.commit()
    }
}