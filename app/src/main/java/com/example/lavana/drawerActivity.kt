package com.example.lavana

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
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
import androidx.fragment.app.Fragment
import androidx.navigation.ui.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.lavana.ui.coachManagement.coachManagementFragment
import com.example.lavana.ui.courtBooking.courtBookingFragment
import com.example.lavana.ui.event.eventFragment
import com.example.lavana.ui.myTrainingClass.myTrainingClassFragment
import com.example.lavana.ui.trainingClass.trainingClassFragment

import android.view.ViewGroup
import com.example.lavana.ui.myBooking.myBookingFragment
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.PersistableBundle


class drawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var loginedName : TextView
    private lateinit var loginedEmail : TextView
    private lateinit var getUserName : String
    private lateinit var profilePic : ImageView
    private lateinit var fragment : Fragment

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
            val sessAccType = sharedPreferences.getString("SESSION_ACCTYPE", "No Type")
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
                    R.id.nav_event, R.id.nav_coachManagement, R.id.nav_courtBooking,
                    R.id.nav_myTrainingClass, R.id.nav_myBooking , R.id.nav_share, R.id.nav_trainingClass, R.id.nav_logout
                ), drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            //navigationView.setupWithNavController(navController)

            //use to check accType user later

            if(sessAccType.equals("coach"))
            {
                val nav_menu : Menu = navigationView.menu
                nav_menu.findItem(R.id.nav_myBooking).setVisible(false)
                nav_menu.findItem(R.id.nav_courtBooking).setVisible(false)
                nav_menu.findItem(R.id.nav_myTrainingClass).setVisible(false)
                nav_menu.findItem(R.id.nav_trainingClass).setVisible(false)
                nav_menu.findItem(R.id.nav_coachManagement).setVisible(false)
            }
            else if(sessAccType.equals("member"))
            {
                val nav_menu : Menu = navigationView.menu
                nav_menu.findItem(R.id.nav_coachManagement).setVisible(false)
            }
            else if(sessAccType.equals("admin"))
            {
                val nav_menu : Menu = navigationView.menu
                nav_menu.findItem(R.id.nav_myBooking).setVisible(false)
                nav_menu.findItem(R.id.nav_courtBooking).setVisible(false)
                nav_menu.findItem(R.id.nav_myTrainingClass).setVisible(false)
                nav_menu.findItem(R.id.nav_trainingClass).setVisible(false)

            }


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

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()


        when(p0.itemId)
        {
            R.id.nav_myBooking ->
            {
                fragment  = myBookingFragment()


                fragmentTransaction.remove(eventFragment())
                fragmentTransaction.add(R.id.nav_host_fragment, fragment)
                fragmentManager.saveFragmentInstanceState(fragment)
                fragmentTransaction.commit()
            }


            R.id.nav_courtBooking ->
            {

                fragment = courtBookingFragment()
                fragmentTransaction.remove(eventFragment())
                fragmentTransaction.add(R.id.nav_host_fragment, fragment)

                fragmentTransaction.commit()

            }


            R.id.nav_myTrainingClass ->
            {

                fragment = myTrainingClassFragment()
                fragmentTransaction.remove(eventFragment())
                fragmentTransaction.add(R.id.nav_host_fragment, fragment)
                fragmentTransaction.commit()

                //Toast.makeText(this, "asdsad", Toast.LENGTH_SHORT).show()

                drawerLayout.closeDrawers()
            }


            R.id.nav_coachManagement ->
            {

                fragment = coachManagementFragment()
                fragmentTransaction.remove(eventFragment())
                fragmentTransaction.add(R.id.nav_host_fragment, fragment)
                fragmentTransaction.commit()
                drawerLayout.closeDrawers()
            }

            R.id.nav_trainingClass ->
            {

                fragment = trainingClassFragment()
                fragmentTransaction.remove(eventFragment())
                fragmentTransaction.add(R.id.nav_host_fragment, fragment)
                fragmentTransaction.commit()
                drawerLayout.closeDrawers()
            }


            R.id.nav_event ->
            {


                fragment = eventFragment()
                fragmentTransaction.add(R.id.nav_host_fragment, fragment)
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


        drawerLayout.closeDrawers()

        return true

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        getSupportFragmentManager().putFragment(outState, "myFragmentName", fragment);
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

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

}
