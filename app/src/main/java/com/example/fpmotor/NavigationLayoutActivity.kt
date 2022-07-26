package com.example.fpmotor

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.fpmotor.UpdateFingerprintFragment.Companion.newInstance
import com.example.fpmotor.databinding.ActivityNavigationLayoutBinding
import com.example.fpmotor.ui.gallery.GalleryFragment
import com.example.fpmotor.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class NavigationLayoutActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
//    private lateinit var binding: ActivityNavigationLayoutBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var toogle:ActionBarDrawerToggle

    lateinit var mAuth:FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_layout)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mAuth = FirebaseAuth.getInstance()
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        toogle = ActionBarDrawerToggle(this, drawerLayout,R.string.open ,R.string.close )
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment(), it.title.toString())
//                    Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
                R.id.nav_gallery ->
                    replaceFragment(GalleryFragment(), it.title.toString())
//                    Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
                R.id.nav_logout ->
                    logout()
//                    Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
            true
        }
        dbRef = FirebaseDatabase.getInstance().getReference("fingerprintAccess")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value.toString()
//                Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
                if (value == "false"){
                    val notificationHelper = NotificationHelper(this@NavigationLayoutActivity)
                    notificationHelper.sendHighPriorityNotification("Fingerprint Access Failed Check Your Motorcycle!",
                        "", HomeFragment::class.java)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@NavigationLayoutActivity, "Failed To Get Data", Toast.LENGTH_LONG).show()
            }
        })

//        binding = ActivityNavigationLayoutBinding.inflate(layoutInflater)
//        setContentView(binding.root)

//        setSupportActionBar(binding.appBarNavigationLayout.toolbar)
//        binding.appBarNavigationLayout.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
//        val drawerLayout: DrawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
//        val navController = findNavController(R.id.nav_host_fragment_content_navigation_layout)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
////                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
//                R.id.nav_home, R.id.nav_gallery
//            ), drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

//        navView.setNavigationItemSelectedListener(this)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.navigation_layout, menu)
//        return true
//    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toogle.onOptionsItemSelected(item)){
            return true
        }
        return super.onContextItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment, title:String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_content_navigation_layout, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }
    private fun logout(){
        val currentUser: FirebaseUser? = mAuth.getCurrentUser()
        if (currentUser == null) {
            Toast.makeText(this, "current user null", Toast.LENGTH_SHORT).show()
        } else {
//            startActivity(Intent(this, NavigationBarActivity::class.java))
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation_layout)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}