package com.example.lavana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class trainingClassCoachActivity : AppCompatActivity() {

    private lateinit var registerBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_class_coach)

        registerBtn = findViewById(R.id.registerButton)

        registerBtn.setOnClickListener{
            val intent = Intent(this, trainingClassCoachActivity::class.java)
            startActivity(intent)
        }


    }
}
