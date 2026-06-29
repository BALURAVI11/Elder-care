package com.explore.eldercare

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.explore.eldercare.databinding.ActivityMainBinding
import com.explore.eldercare.ui.contacts.ContactsFragment
import com.explore.eldercare.ui.dashboard.DashboardFragment
import com.explore.eldercare.ui.donation.DonationFragment
import com.explore.eldercare.ui.healthcare.HealthCareFragment
import com.explore.eldercare.ui.home.HomeFragment
import com.explore.eldercare.ui.notifications.NotificationActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        replaceFragment(HomeFragment())
//
//        val bottomNavigationView : BottomNavigationView =findViewById(R.id.nav_view)
//        bottomNavigationView.setOnItemSelectedListener {
//
//            if(it.itemId == R.id.navigation_home){
//                replaceFragment(HomeFragment())
//            }
//            if (it.itemId == R.id.navigation_contacts){
//                replaceFragment(ContactsFragment())
//            }
//            if(it.itemId== R.id.navigation_dashboard){
//                replaceFragment(DashboardFragment())
//            }
//            if(it.itemId==R.id.navigation_donation){
//                replaceFragment(DonationFragment())
//            }
//            if(it.itemId==R.id.navigation_health_care){
//                replaceFragment(HealthCareFragment())
//            }
//            return@setOnItemSelectedListener true
//        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

//        binding.btnNotifications.setOnClickListener {
//            startActivity(Intent(this@MainActivity,NotificationActivity::class.java))
//        }


    }

    public fun replaceFragment(fragment: Fragment){

        val fragmentTransaction :FragmentTransaction =supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment)
        fragmentTransaction.commit()

    }
}