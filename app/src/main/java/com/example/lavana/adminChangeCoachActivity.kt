package com.example.lavana

import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class adminChangeCoachActivity : AppCompatActivity() {

    lateinit var chgCoachBtn : Button
    lateinit var delCoachBtn : Button
    lateinit var headerImage : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_change_coach)

        var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        var saveSports = sharedPreferences.getString("SELECTED_SPORT", "No Sport")
        var saveTimeSelected = sharedPreferences.getString("SELECTED_TIME", "No Time")
        var saveDaySelected = sharedPreferences.getString("SELECTED_DAY", "No Day")

        setTitle(saveDaySelected.toString() + ", " + saveTimeSelected.toString())

        chgCoachBtn = findViewById(R.id.changeCoachButton)
        delCoachBtn = findViewById(R.id.removeCoachButton)
        headerImage = findViewById(R.id.adminimageView3)

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



        chgCoachBtn.setOnClickListener{

            val intent = Intent(this, adminSelectCoachActivity::class.java)
            startActivity(intent)
            //finish()
        }

    }
}
