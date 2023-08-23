package com.test.test.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.setupWithNavController
import com.test.test.R
import com.test.test.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBottomNavigationView()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.data != null) {
            handleDeepLink(intent.data!!)
        }
        Log.e("#act", "$intent")
    }

    private fun handleDeepLink(deepLink: Uri) {
//        val navController = findNavController(binding.navHostFragment)
//        val navOptions = NavOptions.Builder()
//            .setLaunchSingleTop(true)
//            .setEnterAnim(R.anim.nav_default_enter_anim)
//            .setExitAnim(R.anim.nav_default_exit_anim)
//            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
//            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
//            .build()
//
//        val deepLinkUri = deepLink.toString()
//        navController.navigate(deepLinkUri, null, navOptions)
    }

    private fun setUpBottomNavigationView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = binding.navView

        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val bottomNavMenus = listOf(
                R.id.dashboardFragment,
                R.id.analysisFragment,
                R.id.newsFragment,
                R.id.profileFragment
            )

            if (bottomNavMenus.contains(destination.id)) {
                bottomNavigationView.visibility = View.VISIBLE
            } else {
                bottomNavigationView.visibility = View.GONE
            }
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            navController.popBackStack()

            val destinationId = item.itemId
            val currentDestinationId = navController.currentDestination?.id

            if (destinationId != currentDestinationId) {
                navController.navigate(destinationId)
            }
            true
        }
    }
}