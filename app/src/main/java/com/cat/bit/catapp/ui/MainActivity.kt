package com.cat.bit.catapp.ui

import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.cat.bit.catapp.R
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity(),
    ListFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {

    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        navView.setupWithNavController(navController)
    }
}
