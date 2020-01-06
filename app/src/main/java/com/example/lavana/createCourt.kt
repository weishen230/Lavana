package com.example.lavana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_court.*

class createCourt : AppCompatActivity() {

    private lateinit var courtTxt : TextView
    private lateinit var sportTxt : TextView
    private lateinit var typeTxt : TextView
    private lateinit var perhourTxt : TextView
    private lateinit var button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_court)

        button = findViewById(R.id.button2)
        courtTxt = findViewById(R.id.courtEdit)
        sportTxt = findViewById(R.id.sportEdit)
        typeTxt = findViewById(R.id.indoorOutdoorEdit)
        perhourTxt = findViewById(R.id.perhourEdit)


        button.setOnClickListener{

            val firebase = FirebaseDatabase.getInstance().getReference("court")

            var key = firebase.push().key.toString()

            var court = courtClass(courtTxt.text.toString(), sportTxt.text.toString(), typeTxt.text.toString(), perhourTxt.text.toString())

            firebase.child(key).setValue(court).addOnCompleteListener{

            }



        }


    }
}
