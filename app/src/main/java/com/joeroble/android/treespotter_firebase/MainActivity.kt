package com.joeroble.android.treespotter_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


private const val TAG = "MAIN_ACTIVITY"
class MainActivity : AppCompatActivity() {

    val CURRENT_FRAGMENT_BUNDLE_KEY = "current fragment bundle key"
    var currentFragmentTag = "MAP"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentFragmentTag = savedInstanceState?.getString(CURRENT_FRAGMENT_BUNDLE_KEY) ?: "MAP"
        showFragment(currentFragmentTag)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.show_map ->{
                    showFragment("MAP")
                    true
                }
                R.id.show_list -> {
                    showFragment("LIST")
                    true
                }
                else -> {
                    false
                }
            }
        }



    }

    private fun showFragment(tag: String){
        // if we are not seeing the fragment with the given tag, display it
        currentFragmentTag = tag

        if (supportFragmentManager.findFragmentByTag(tag) == null){
            val transaction = supportFragmentManager.beginTransaction()
            when (tag){
                "MAP"-> transaction.replace(R.id.fragmentContainerView, TreeMapFragment.newInstance(), "MAP")
                "LIST" -> transaction.replace(R.id.fragmentContainerView, TreeListFragment.newInstance(), "LIST")
            }
            transaction.commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_FRAGMENT_BUNDLE_KEY, currentFragmentTag)
    }
}