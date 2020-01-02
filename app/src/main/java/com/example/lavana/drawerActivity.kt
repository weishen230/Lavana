package com.example.lavana

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu

import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.ui.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.lavana.ui.coachManagement.coachManagementFragment
import com.example.lavana.ui.event.eventFragment

class drawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var loginedName : TextView
    private lateinit var loginedEmail : TextView
    private lateinit var getUserName : String
    private lateinit var profilePic : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)

        var checkPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        val username = checkPreferences.getString("SESSION_ID", getString(R.string.sharedPreUsername))

        if(!(username.equals("No name")))
        {


            val toolbar: Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)

            val fab: FloatingActionButton = findViewById(R.id.fab)
            fab.setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

            drawerLayout= findViewById(R.id.drawer_layout)

            /*val toggle = ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close)

            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()*/

            navigationView = findViewById(R.id.nav_view)

            //to get the logined username and email
            val headerView : View = navigationView.getHeaderView(0)
            loginedName = headerView.findViewById(R.id.loginHeaderName)
            loginedEmail = headerView.findViewById(R.id.loginEmailHeader)
            profilePic = headerView.findViewById(R.id.headerProPic)

            var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
            val sessionId = sharedPreferences.getString("SESSION_ID", getString(R.string.sharedPreUsername))
            val sessionEmail  = sharedPreferences.getString("SESSION_EMAIL", getString(R.string.sharedPreEmail))
            val sessProPic = sharedPreferences.getString("SESSION_PROPIC", "No Pic")
            loginedName.setText(sessionId.toString())
            loginedEmail.setText(sessionEmail.toString())

            //Log.i("nah", sessProPic.toString())

            if(!(sessProPic.equals("No Pic") || sessProPic.equals("") ))
            {

                var url : Uri = Uri.parse(sessProPic)
                Log.i("hi", url.toString())
                Glide
                    .with(this)
                    .load(url)
                    .override(100,100)
                    .apply(RequestOptions.circleCropTransform()).into(profilePic);
                //profilePic.setImageURI(Uri.parse(sessProPic))
            }
            else
            {
                profilePic.setImageResource(R.drawable.ic_launcher_round)

            }



            navigationView.setNavigationItemSelectedListener(this)

            val navController = findNavController(R.id.nav_host_fragment)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_event, R.id.nav_coachManagement, R.id.nav_slideshow,
                    R.id.nav_tools, R.id.nav_share, R.id.nav_send, R.id.nav_logout
                ), drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            //navigationView.setupWithNavController(navController)

            //use to check accType user later
            //val nav_menu : Menu = navigationView.menu
            //nav_menu.findItem(R.id.nav_event).setVisible(false)

        }
        else
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }







    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

       // return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp()
    }

    //navigation drawer menu item selected
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {

        when(p0.itemId)
        {

            R.id.nav_coachManagement ->
            {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val fragment = coachManagementFragment()
                fragmentTransaction.remove(eventFragment())
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                drawerLayout.closeDrawers()
            }


            R.id.nav_event ->
            {

                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val fragment = eventFragment()
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                drawerLayout.closeDrawers()
            }


            R.id.nav_logout ->
            {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
                sharedPreferences.edit().clear().commit()

                finish()
                //drawerLayout.closeDrawers()
            }






        }

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.account_settings ->
            {
                val intent = Intent(this, accountSettingActivity::class.java)
                startActivity(intent)
                Log.i("hi", "ganneh")
                //finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }



}
