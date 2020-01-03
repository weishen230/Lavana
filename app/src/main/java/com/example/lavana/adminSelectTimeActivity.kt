package com.example.lavana

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class adminSelectTimeActivity : AppCompatActivity() {

    lateinit var headerImage : ImageView
    lateinit var mondayClass1Btn : Button
    lateinit var mondayClass2Btn : Button
    lateinit var mondayClass3Btn : Button
    lateinit var tuesdayClass1Btn : Button
    lateinit var tuesdayClass2Btn : Button
    lateinit var tuesdayClass3Btn : Button
    lateinit var wednesdayClass1Btn : Button
    lateinit var wednesdayClass2Btn : Button
    lateinit var wednesdayClass3Btn : Button
    lateinit var thursdayClass1Btn : Button
    lateinit var thursdayClass2Btn : Button
    lateinit var thursdayClass3Btn : Button
    lateinit var fridayClass1Btn : Button
    lateinit var fridayClass2Btn : Button
    lateinit var fridayClass3Btn : Button
    lateinit var textheader : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_select_time)




        var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        var saveSports = sharedPreferences.getString("SELECTED_SPORT", "No Sport")

        setTitle(saveSports.toString())

        mondayClass1Btn = findViewById(R.id.adminmondayClass1_button)
        mondayClass2Btn = findViewById(R.id.adminmondayClass2_button)
        mondayClass3Btn = findViewById(R.id.adminmondayClass3_button)

        tuesdayClass1Btn = findViewById(R.id.admintuesdayClass1_button)
        tuesdayClass2Btn = findViewById(R.id.admintuesdayClass2_button)
        tuesdayClass3Btn = findViewById(R.id.admintuesdayClass3_button)

        wednesdayClass1Btn = findViewById(R.id.adminwednesdayClass1_button)
        wednesdayClass2Btn = findViewById(R.id.adminwednesdayClass2_button)
        wednesdayClass3Btn = findViewById(R.id.adminwednesdayClass3_button)

        thursdayClass1Btn = findViewById(R.id.adminthursdayClass1_button)
        thursdayClass2Btn = findViewById(R.id.adminthursdayClass2_button)
        thursdayClass3Btn = findViewById(R.id.adminthursdayClass3_button)

        fridayClass1Btn = findViewById(R.id.adminfridayClass1_button)
        fridayClass2Btn = findViewById(R.id.adminfridayClass2_button)
        fridayClass3Btn = findViewById(R.id.adminfridayClass3_button)

        textheader = findViewById(R.id.admintextView6)
        textheader.setText(saveSports + " Class Time Table")

        var saveTimeSelected = sharedPreferences.getString("SELECTED_TIME", "No Time")?:return
        var saveDaySelected = sharedPreferences.getString("SELECTED_DAY", "No Day")?:return

        headerImage = findViewById(R.id.adminimageView2)

        if(saveSports.equals("Badminton"))
        {
            headerImage.setImageResource(R.drawable.badminton)
        }
        else if(saveSports.equals("Volleyball"))
        {
            headerImage.setImageResource(R.drawable.volleyball_logo)
        }
        else if(saveSports.equals("Basketball"))
        {
            headerImage.setImageResource(R.drawable.basketball)
        }
        else if(saveSports.equals("Football"))
        {
            headerImage.setImageResource(R.drawable.football)
        }





        //monday class button on click
        mondayClass1Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", mondayClass1Btn.text.toString())
                putString("SELECTED_DAY", "Monday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()

        }

        mondayClass2Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", mondayClass2Btn.text.toString())
                putString("SELECTED_DAY", "Monday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()
        }

        mondayClass3Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", mondayClass3Btn.text.toString())
                putString("SELECTED_DAY", "Monday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()
        }

        //tuesday class button on click
        tuesdayClass1Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", tuesdayClass1Btn.text.toString())
                putString("SELECTED_DAY", "Tuesday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()

        }

        tuesdayClass2Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", tuesdayClass2Btn.text.toString())
                putString("SELECTED_DAY", "Tuesday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()

        }

        tuesdayClass3Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", tuesdayClass3Btn.text.toString())
                putString("SELECTED_DAY", "Tuesday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()

        }

        //wednesday class button on click
        wednesdayClass1Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", wednesdayClass1Btn.text.toString())
                putString("SELECTED_DAY", "Wednesday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()

        }

        wednesdayClass2Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", wednesdayClass2Btn.text.toString())
                putString("SELECTED_DAY", "Wednesday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()

        }

        wednesdayClass3Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", wednesdayClass3Btn.text.toString())
                putString("SELECTED_DAY", "Wednesday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()

        }

        //thursday class button on click
        thursdayClass1Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", thursdayClass1Btn.text.toString())
                putString("SELECTED_DAY", "Thursday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()

        }

        thursdayClass2Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", thursdayClass2Btn.text.toString())
                putString("SELECTED_DAY", "Thursday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()

        }

        thursdayClass3Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", thursdayClass2Btn.text.toString())
                putString("SELECTED_DAY", "Thursday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()

        }

        //friday class button on click
        fridayClass1Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", fridayClass1Btn.text.toString())
                putString("SELECTED_DAY", "Friday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()

        }

        fridayClass2Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", fridayClass2Btn.text.toString())
                putString("SELECTED_DAY", "Friday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()

        }

        fridayClass3Btn.setOnClickListener{

            with(sharedPreferences.edit())
            {
                putString("SELECTED_TIME", fridayClass3Btn.text.toString())
                putString("SELECTED_DAY", "Friday")
                apply()
            }

            val intent = Intent(this, adminChangeCoachActivity::class.java)
            startActivity(intent)
            //finish()

        }


    }

    fun buttonInitialization()
    {

    }

    fun checkSport()
    {

    }
}
