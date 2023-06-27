package com.thisisbkm.gatepassmanagementsystem

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.thisisbkm.gatepassmanagementsystem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var viewPager: ViewPager

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        // Find the view pager that will allow the user to swipe between fragments
        viewPager = findViewById(R.id.viewpager)


        // Give the TabLayout the ViewPager
        val tabLayout = findViewById<TabLayout>(R.id.sliding_tabs)
        tabLayout.setupWithViewPager(viewPager)
        // Set gravity for tab bar
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val navigationView = findViewById<NavigationView>(R.id.nav_view)!!
        navigationView.setNavigationItemSelectedListener(this)

        // Set the default fragment when starting the app
        onNavigationItemSelected(navigationView.menu.getItem(0).setChecked(true))

        // Set category fragment pager adapter
        val pagerAdapter = CategoryFragmentPagerAdapter(
            this,
            supportFragmentManager
        )
        // Set the pager adapter onto the view pager
//        viewPager.setAdapter(pagerAdapter)
        viewPager.adapter = pagerAdapter
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        // Switch Fragments in a ViewPager on clicking items in Navigation Drawer
        if (id == R.id.checkin) {
            viewPager.currentItem = Constants.CHECKIN
        } else if (id == R.id.checkout) {
            viewPager.currentItem = Constants.CHECKOUT
        }else if(id == R.id.logs){
            viewPager.currentItem = Constants.LOGS
        }else if(id == R.id.scanner){
            viewPager.currentItem = Constants.SCANNER
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}