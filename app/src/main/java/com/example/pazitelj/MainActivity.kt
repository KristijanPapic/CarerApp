package com.example.pazitelj

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pazitelj.databinding.ActivityMainBinding
import com.example.pazitelj.ui.CurrentUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var currentNavController: LiveData<NavController>? = null

    private val onDestinationChangedListener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->


            // if you need to show/hide bottom nav or toolbar based on destination
            // binding.bottomNavigationView.isVisible = destination.id != R.id.itemDetail
        }

    private val currentUser: CurrentUserViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.top_bar2)
        setSupportActionBar(toolbar)
            setUpBottomNavigationBar()



        val userId = intent?.extras?.getString("userId").toString()
        CoroutineScope(IO).launch {
            currentUser.getUser(userId)
        }

    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNavigationBar()
    }


    @RequiresApi(Build.VERSION_CODES.S)
    private fun setUpBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.nav_tab_home,
            R.navigation.nav_tab_post,
            R.navigation.nav_tab_profile
        )
        val controller = binding.navView.setupWithNavController(navGraphIds = navGraphIds, fragmentManager = supportFragmentManager, containerId = R.id.nav_host_fragment_container,intent = intent)

        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller

        currentNavController?.value?.removeOnDestinationChangedListener(
            onDestinationChangedListener
        )

    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onBackPressed() {
        if (currentNavController?.value?.popBackStack() != true) {
            super.onBackPressed()
        }
    }


}